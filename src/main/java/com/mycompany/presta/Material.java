/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.presta;

import java.util.Date;

/**
 *
 * @author Green
 */
public class Material {

    private int id;
    private String nombre;
    private String descripcion;
    private int cantidad;
    private String potenciador;
    private boolean necesitaPotencia;
    private Date fechaPedido;
    private Date fechaDevuelto;
    private boolean fueDevuelto;

    public Material(int id, String nombre, boolean necesitaPotencia) {
        this.id = id;
        this.nombre = nombre;
        descripcion = " ";
        this.necesitaPotencia = necesitaPotencia;
    }

    public Material() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    public String getPotenciador() {
        return potenciador;
    }

    public void setPotenciador(String potenciador) {
        this.potenciador = potenciador;
    }

    public boolean isNecesitaPotencia() {
        return necesitaPotencia;
    }

    public void setNecesitaPotencia(boolean necesitaPotencia) {
        this.necesitaPotencia = necesitaPotencia;
    }

    public Date getFechaPedido() {
        return fechaPedido;
    }

    public void setFechaPedido(Date fechaPedido) {
        this.fechaPedido = fechaPedido;
    }

    public Date getFechaDevuelto() {
        return fechaDevuelto;
    }

    public void setFechaDevuelto(Date fechaDevuelto) {
        this.fechaDevuelto = fechaDevuelto;
    }

    public boolean isFueDevuelto() {
        return fueDevuelto;
    }

    public void setFueDevuelto(boolean fueDevuelto) {
        this.fueDevuelto = fueDevuelto;
    }

    @Override
    public String toString() {
        return "Material{" + "id=" + id + ", nombre=" + nombre + ", descripcion=" + descripcion + ", cantidad=" + cantidad + ", potenciador=" + potenciador + ", necesitaPotencia=" + necesitaPotencia + ", fechaPedido=" + fechaPedido + ", fechaDevuelto=" + fechaDevuelto + ", fueDevuelto=" + fueDevuelto + '}';
    }

}
