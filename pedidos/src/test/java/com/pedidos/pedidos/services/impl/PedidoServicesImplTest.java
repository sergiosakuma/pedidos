/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package com.pedidos.pedidos.services.impl;

import com.pedidos.pedidos.model.Pedido;
import com.pedidos.pedidos.model.Producto;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author kenji
 */
public class PedidoServicesImplTest {

    public PedidoServicesImplTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of getProducto method, of class PedidoServicesImpl.
     *
     * @throws java.lang.Exception
     */
    @Test
    public void testGetProducto() throws Exception {
        System.out.println("getProducto");
        String id = "p1";
        PedidoServicesImpl instance = new PedidoServicesImpl();
        Producto result = instance.getProducto(id);
        assertNotNull(result);
    }

    /**
     * Test of saveProducto method, of class PedidoServicesImpl.
     */
    @Test
    public void testSaveProducto() throws Exception {
        Date d = new Date();
        System.out.println("saveProducto");
        Producto producto = new Producto("p" + d.getSeconds(), "Nombre " + d.getTime());
        PedidoServicesImpl instance = new PedidoServicesImpl();
        instance.saveProducto(producto);
    }

    /**
     * Test of saveProducto method, of class PedidoServicesImpl.
     */
    @Test
    public void testUpdateProducto() throws Exception {
        Date d = new Date();
        System.out.println("saveProducto");
        Producto producto = new Producto("p1", "Nombre modificado " + d.getTime());
        PedidoServicesImpl instance = new PedidoServicesImpl();
        instance.updateProducto(producto);
    }

    /**
     * Test of deleteProducto method, of class PedidoServicesImpl.
     */
    @Test
    public void testDeleteProducto() throws Exception {
        System.out.println("deleteProducto");
        String id = "p38";
        PedidoServicesImpl instance = new PedidoServicesImpl();
        instance.deleteProducto(id);
    }

    /**
     * Test of gePedido method, of class PedidoServicesImpl.
     */
    @Test
    public void testGetPedido() throws Exception {
        System.out.println("gePedido");
        Integer id = 2;
        PedidoServicesImpl instance = new PedidoServicesImpl();
        Pedido result = instance.getPedido(id);
        assertNotNull(result);
    }

    /**
     * Test of savePedido method, of class PedidoServicesImpl.
     */
    @Test
    public void testSavePedido() throws Exception {
        Date d = new Date();
        Random r = new Random();
        int num = r.nextInt(999999999);
        System.out.println("savePedido");
        List<Producto> prods = new ArrayList<>();
        prods.add(new Producto("p1", "producto " + num));
        prods.add(new Producto("p2", "producto " + num));
        prods.add(new Producto("p5", "producto " + num));

        Pedido pedido = new Pedido(null, "Pedido " + d.getTime(), prods);
        PedidoServicesImpl instance = new PedidoServicesImpl();
        instance.savePedido(pedido);

    }

    /**
     * Test of updatePedido method, of class PedidoServicesImpl.
     */
    @Test
    public void testUpdatePedido() throws Exception {
        Date d = new Date();
        Random r = new Random();
        int num = r.nextInt(999999999);
        System.out.println("savePedido");
        List<Producto> prods = new ArrayList<>();
        prods.add(new Producto("p5", "producto " + num));
        prods.add(new Producto("p6", "producto " + num));

        Pedido pedido = new Pedido(18, "Pedido " + d.getTime(), prods);
        PedidoServicesImpl instance = new PedidoServicesImpl();
        instance.updatePedido(pedido);
    }

    /**
     * Test of deletePedido method, of class PedidoServicesImpl.
     */
    @Test
    public void testDeletePedido() throws Exception {
        System.out.println("deletePedido");
        Integer id = 18;
        PedidoServicesImpl instance = new PedidoServicesImpl();
        instance.deletePedido(id);
    }

    /**
     * Test of testFindPedido method, of class PedidoServicesImpl.
     */
    @Test
    public void testFindPedido() throws Exception {
        System.out.println("findPedido");
        PedidoServicesImpl instance = new PedidoServicesImpl();
        List<Pedido> res = instance.findPedido("PEDIDO", "PROD", null, null);
        System.out.println("size: " + res.size());

        assertTrue(res.size() > 0);
    }

    /**
     * Test of testFindProducto method, of class PedidoServicesImpl.
     */
    @Test
    public void testFindProducto() throws Exception {
        System.out.println("FindProducto");
        PedidoServicesImpl instance = new PedidoServicesImpl();
        List<Producto> res = instance.findProducto("p", "PROD");
        System.out.println("size: " + res.size());

        assertTrue(res.size() > 0);
    }

    /**
     * Test of findProductoPedido method, of class PedidoServicesImpl.
     */
    @Test
    public void testFindProductoPedido() throws Exception {
        System.out.println("findProductoPedido");
        PedidoServicesImpl instance = new PedidoServicesImpl();
        List<Producto> res = instance.findProductoPedido(5);
        System.out.println("size: " + res.size());

        assertTrue(res.size() > 0);
    }
}
