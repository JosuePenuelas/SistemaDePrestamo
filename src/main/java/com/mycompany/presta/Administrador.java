/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.presta;

import java.util.ArrayList;

/**
 *
 * @author Green
 */
public class Administrador extends Usuario {

    private ArrayList<Material> inventario;

    public Administrador(ArrayList<Material> inventario, String nombre, String correo, int tipoUsuario) {
        super(nombre, correo, tipoUsuario);
        this.inventario = inventario;
    }

    public Administrador(ArrayList<Material> inventario) {
        this.inventario = inventario;
    }

    public ArrayList<Material> getInventario() {
        return inventario;
    }

    public void setInventario(ArrayList<Material> inventario) {
        this.inventario = inventario;
    }

    public void agregarMaterialInventario(Material material){
        inventario.add(material);
    }
    
}
