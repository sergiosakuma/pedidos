/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pedidos.pedidos.services.impl;

import com.pedidos.pedidos.common.ValidationException;
import com.pedidos.pedidos.model.Pedido;
import com.pedidos.pedidos.model.Producto;
import com.pedidos.pedidos.services.PedidosServices;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author kenji
 */
@Service
public class PedidoServicesImpl implements PedidosServices {

    //private final String DRIVER = "org.apache.derby.jdbc.EmbeddedDriver";
    private final String DRIVER = "org.apache.derby.jdbc.ClientDriver";
    //private final String PROTOCOL = "jdbc:derby:";
    private final String PROTOCOL = "jdbc:derby://localhost:1527/";
    private static Connection conn = null;

    public PedidoServicesImpl() throws Exception {
        //DriverManager.registerDriver(new EmbeddedDriver());
        Class.forName(DRIVER).newInstance();
        conn = DriverManager.getConnection(PROTOCOL + "Pedidos;create=true");
    }

    public Connection getConn() {
        return conn;
    }

    @Override
    public Producto getProducto(String id) throws Exception {
        String query = "select * from productos where id='" + id + "'"
                + " and disabled=false";

        try ( PreparedStatement p = conn.prepareStatement(query)) {
            ResultSet res = p.executeQuery();

            if (res.next()) {
                Producto pr = new Producto(res.getString(1), res.getString(2));
                return pr;
            }

            return null;
        }
    }

    @Override
    public void saveProducto(Producto producto) throws ValidationException, SQLException {

        if (producto.getId() == null || producto.getId().isEmpty()) {
            throw new ValidationException("Falta id del producto.");
        }

        String query = "insert into productos (id, nombre) values ('" + producto.getId() + "','"
                + producto.getNombre() + "')";
        int res;

        try ( PreparedStatement p = conn.prepareStatement(query)) {
            res = p.executeUpdate();
        }

        if (res <= 0) {
            throw new ValidationException("No se pudo registrar.");
        }
    }

    @Override
    public void updateProducto(Producto producto) throws ValidationException, SQLException {

        if (producto.getId() == null || producto.getId().isEmpty()) {
            throw new ValidationException("Falta id del pedido.");
        }

        String query = "update productos set nombre='" + producto.getNombre()
                + "' where id='" + producto.getId().trim() + "'";
        int res = 0;

        try ( PreparedStatement p = conn.prepareStatement(query)) {
            res = p.executeUpdate();
        }

        if (res <= 0) {
            throw new ValidationException("No se pudo registrar.");
        }
    }

    @Override
    public void deleteProducto(String id) throws Exception {
        String query = "update productos set disabled=true where id='" + id + "'";
        int res;

        try ( PreparedStatement p = conn.prepareStatement(query)) {
            res = p.executeUpdate();
        }

        if (res <= 0) {
            throw new Exception("No se pudo eliminar.");
        }
    }

    @Override
    public Pedido getPedido(Integer id) throws Exception {
        String query = "select * from pedidos where id=" + id
                + " and disabled=false";

        try ( PreparedStatement p = conn.prepareStatement(query)) {
            ResultSet res = p.executeQuery();

            if (res.next()) {
                Pedido pr = new Pedido(res.getInt(1), res.getString(2), res.getTimestamp(3));
                return pr;
            }

            return null;
        }

        //throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void savePedido(Pedido pedido) throws Exception {
        SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String query = "insert into pedidos (descripcion,fecha) values ('" + pedido.getDescripcion() + "','"
                + dformat.format(pedido.getFecha()) + "')";
        int res;

        try ( Statement p = conn.createStatement()) {
            p.executeUpdate(query, new String[]{"id"});
            ResultSet key = p.getGeneratedKeys();
            key.next();
            pedido.setId(key.getInt(1));

            //Registrando relacion pedido producto
            if (pedido.getProductos() != null) {
                String q2 = "insert into pedidos_productos (id_pedido, id_producto) values (%s,'"
                        + "%s')";

                for (Producto _p : pedido.getProductos()) {
                    String _q = String.format(q2, pedido.getId(), _p.getId());
                    int c = p.executeUpdate(_q);

                    if (c <= 0) {
                        throw new Exception("No se pudo registrar producto asociado.");
                    }
                }
            }
        }
    }

    @Override
    public void updatePedido(Pedido pedido) throws ValidationException, SQLException {

        if (pedido.getId() == null) {
            throw new ValidationException("Falta id del pedido.");
        }

        String query = "update pedidos set descripcion='" + pedido.getDescripcion() + "'"
                + " where id=" + pedido.getId();
        int res;

        try ( Statement p = conn.createStatement()) {
            res = p.executeUpdate(query);

            if (res <= 0) {
                throw new ValidationException("No se pudo registrar.");
            }

            //Eliminando las asociaciones con los productos para volver a asociar
            p.executeUpdate("update pedidos_productos set disabled=true where id_pedido=" + pedido.getId());

            //Registrando relacion pedido producto
            if (pedido.getProductos() != null) {
                String q2 = "insert into pedidos_productos (id_pedido, id_producto) values (%s,'"
                        + "%s')";

                for (Producto _p : pedido.getProductos()) {
                    String _q = String.format(q2, pedido.getId(), _p.getId());
                    int c = p.executeUpdate(_q);

                    if (c <= 0) {
                        throw new ValidationException("No se pudo registrar producto asociado.");
                    }
                }
            }
        }
    }

    @Override
    public void deletePedido(Integer id) throws Exception {
        String query = "update pedidos set disabled=true where id=" + id;
        int res;

        try ( PreparedStatement p = conn.prepareStatement(query)) {
            res = p.executeUpdate();
        }

        if (res <= 0) {
            throw new Exception("No se pudo eliminar.");
        }
    }

    @Override
    public List<Pedido> findPedido(String descripcion, String productoNombre,
            Date fechaInicio, Date fechaFin) throws ValidationException, SQLException {

        if (descripcion == null && productoNombre == null
                && fechaInicio == null && fechaFin == null) {
            throw new ValidationException("Debe enviar al menos un criterio de búsqueda.");
        }

        SimpleDateFormat dFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        StringBuilder query = new StringBuilder();
        query.append("SELECT distinct pe.id as pedidoId, pe.DESCRIPCION, pe.FECHA "
                + "FROM pedidos pe left join pedidos_productos pp on pp.id_pedido=pe.ID "
                + "inner join productos pr on pr.ID=pp.ID_PRODUCTO "
                + "where (pe.disabled=false and pp.disabled=false and pr.disabled=false)");

        if (descripcion != null && !descripcion.isEmpty()) {
            query.append(" and pe.descripcion like '%").append(descripcion).append("%'");
        }

        if (productoNombre != null && !productoNombre.isEmpty()) {
            query.append(" and pr.NOMBRE like '%").append(productoNombre).append("%'");
        }

        if (fechaInicio != null) {
            query.append(" and pe.FECHA >= '").append(dFormat.format(fechaInicio)).append("'");
        }

        if (fechaFin != null) {
            query.append(" and pe.FECHA <= '").append(dFormat.format(fechaFin)).append("'");
        }

        try ( PreparedStatement p = conn.prepareStatement(query.toString())) {
            ResultSet res = p.executeQuery();
            List<Pedido> pedL = new ArrayList<>();

            while (res.next()) {
                pedL.add(new Pedido(res.getInt(1), res.getString(2), res.getDate(3)));
            }

            return pedL;
        }
    }

    @Override
    public List<Producto> findProducto(String id, String nombre) throws ValidationException, SQLException {

        if (id == null && nombre == null) {
            throw new ValidationException("Debe enviar al menos un criterio de búsqueda.");
        }

        StringBuilder query = new StringBuilder();
        query.append("SELECT * FROM PRODUCTOS where disabled = false");

        if (id != null && !id.isEmpty()) {
            query.append(" and id like '%").append(id).append("%'");
        }

        if (nombre != null && !nombre.isEmpty()) {
            query.append(" and NOMBRE like '%").append(nombre).append("%'");
        }

        try ( PreparedStatement p = conn.prepareStatement(query.toString())) {
            ResultSet res = p.executeQuery();
            List<Producto> pedL = new ArrayList<>();

            while (res.next()) {
                pedL.add(new Producto(res.getString(1), res.getString(2)));
            }

            return pedL;
        }
    }

    @Override
    public List<Producto> findProductoPedido(Integer pedidoId) throws Exception {
        if (pedidoId == null) {
            throw new ValidationException("Debe enviar id del pedido.");
        }

        StringBuilder query = new StringBuilder();
        query.append("SELECT distinct pe.id as pedidoId, pe.DESCRIPCION, pe.FECHA "
                + "                FROM pedidos pe left join pedidos_productos pp on pp.id_pedido=pe.ID "
                + "                where (pe.disabled=false and pp.disabled=false)"
                + "                and pe.id=").append(pedidoId);

        try ( PreparedStatement p = conn.prepareStatement(query.toString())) {
            ResultSet res = p.executeQuery();
            List<Producto> pedL = new ArrayList<>();

            while (res.next()) {
                pedL.add(new Producto(res.getString(1), res.getString(2)));
            }

            return pedL;
        }
    }

}
