/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.pedidos.pedidos.model;

import java.util.Date;
import java.util.List;

/**
 *
 * @author kenji
 */
public class Pedido {

    private Integer id;
    private String descripcion;
    private Date fecha;
    private List<Producto> productos;

    public Pedido() {
    }

    public Pedido(Integer id, String descripcion, List productos) {
        this.id = id;
        this.descripcion = descripcion;
        this.productos = productos;
        this.fecha = new Date();
    }

    public Pedido(Integer id, String descripcion, Date fecha) {
        this.id = id;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public List<Producto> getProductos() {
        return productos;
    }

    public void setProductos(List<Producto> productos) {
        this.productos = productos;
    }

}
