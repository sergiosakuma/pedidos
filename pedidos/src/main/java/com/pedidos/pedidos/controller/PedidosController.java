/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pedidos.pedidos.controller;

import com.pedidos.pedidos.common.ValidationException;
import com.pedidos.pedidos.common.ValidationForm;
import com.pedidos.pedidos.model.Pedido;
import com.pedidos.pedidos.model.Producto;
import com.pedidos.pedidos.services.PedidosServices;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author kenji
 */
@RestController
public class PedidosController {

    @Autowired
    private PedidosServices service;

    @GetMapping("/producto/{id}")
    public ResponseEntity getProducto(@PathVariable String id) {

        try {
            Producto data = service.getProducto(id);

            return new ResponseEntity(data, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/producto")
    public ResponseEntity registProducto(@RequestBody Producto producto) {
        //Validando datos
        Map valRes = ValidationForm.getViolationsMessage(producto);

        if (!(Boolean) valRes.get("success")) {
            return new ResponseEntity(valRes.get("result"), HttpStatus.BAD_REQUEST);
        }

        try {
            service.saveProducto(producto);

            return new ResponseEntity("Registrado", HttpStatus.OK);
        } catch (ValidationException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (SQLException ex) {
            Logger.getLogger(PedidosController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/producto")
    public ResponseEntity updateProducto(@RequestBody Producto producto) {
        //Validando datos
        Map valRes = ValidationForm.getViolationsMessage(producto);

        if (!(Boolean) valRes.get("success")) {
            return new ResponseEntity(valRes.get("result"), HttpStatus.BAD_REQUEST);
        }

        try {
            service.updateProducto(producto);

            return new ResponseEntity("Registrado", HttpStatus.OK);
        } catch (ValidationException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (SQLException ex) {
            Logger.getLogger(PedidosController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/producto/{id}")
    public ResponseEntity deleteProducto(@PathVariable String id) {

        try {
            service.deleteProducto(id);

            return new ResponseEntity("Eliminado", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/pedido/{id}")
    public ResponseEntity getPedido(@PathVariable Integer id) {

        try {
            Pedido data = service.getPedido(id);

            return new ResponseEntity(data, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/pedido")
    public ResponseEntity registPedido(@RequestBody Pedido pedido) {
        //Validando datos
        Map valRes = ValidationForm.getViolationsMessage(pedido);

        if (!(Boolean) valRes.get("success")) {
            return new ResponseEntity(valRes.get("result"), HttpStatus.BAD_REQUEST);
        }

        try {
            service.savePedido(pedido);

            return new ResponseEntity("Registrado", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/pedido")
    public ResponseEntity updatePedido(@RequestBody Pedido pedido) {
        //Validando datos
        Map valRes = ValidationForm.getViolationsMessage(pedido);

        if (!(Boolean) valRes.get("success")) {
            return new ResponseEntity(valRes.get("result"), HttpStatus.BAD_REQUEST);
        }

        try {
            service.updatePedido(pedido);

            return new ResponseEntity("Registrado", HttpStatus.OK);
        } catch (ValidationException ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (SQLException ex) {
            Logger.getLogger(PedidosController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity(ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/pedido/{id}")
    public ResponseEntity deletePedido(@PathVariable Integer id) {

        try {
            service.deletePedido(id);

            return new ResponseEntity("Eliminado", HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/pedido/productos/{id}")
    public ResponseEntity getPedidoProductos(@PathVariable Integer id) {

        try {
            List<Producto> data = service.findProductoPedido(id);

            return new ResponseEntity(data, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/pedido")
    public ResponseEntity findPedido(
            @RequestParam(required = false) String descripcion,
            @RequestParam(required = false) String producto,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd/MM/yyyy") Date fechaFin
    ) {

        try {
            List<Pedido> data = service.findPedido(descripcion, producto, fechaInicio, fechaFin);

            return new ResponseEntity(data, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/producto")
    public ResponseEntity findProducto(
            @RequestParam(required = false) String id,
            @RequestParam(required = false) String nombre
    ) {

        try {
            List<Producto> data = service.findProducto(id, nombre);

            return new ResponseEntity(data, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
