/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pedidos.pedidos.services;

import com.pedidos.pedidos.common.ValidationException;
import com.pedidos.pedidos.model.Pedido;
import com.pedidos.pedidos.model.Producto;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author kenji
 */
public interface PedidosServices {

    Producto getProducto(String id) throws Exception;

    void saveProducto(Producto producto) throws ValidationException, SQLException;

    void updateProducto(Producto producto) throws ValidationException, SQLException;

    void deleteProducto(String id) throws Exception;

    Pedido getPedido(Integer id) throws Exception;

    void savePedido(Pedido pedido) throws Exception;

    void updatePedido(Pedido pedido) throws ValidationException, SQLException;

    void deletePedido(Integer id) throws Exception;

    /**
     * Busqueda de pedido
     *
     * @param descripcion descripcion del pedido
     * @param productoNombre nombre de producto
     * @param fechaInicio fecha de registro inicial
     * @param fechaFin fecha de registro final
     * @return Lista de pedidos
     * @throws Exception
     */
    List<Pedido> findPedido(String descripcion, String productoNombre, Date fechaInicio, Date fechaFin) throws Exception;

    /**
     * Busca un producto por su nombre
     *
     * @param nombre
     * @param id
     * @return Lista de productos
     * @throws Exception
     */
    List<Producto> findProducto(String id, String nombre) throws Exception;

    /**
     * Lista de productos asignados a un pedido
     *
     * @param pedidoId id del pedido
     * @return listado de productos
     * @throws Exception
     */
    List<Producto> findProductoPedido(Integer pedidoId) throws Exception;
}
