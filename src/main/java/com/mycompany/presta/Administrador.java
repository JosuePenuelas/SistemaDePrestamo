/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.presta;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Green
 */
public class Administrador extends Usuario {

    private ArrayList<Material> inventario;
    private int Id;

    public Administrador(int Id, String nombre, String correo, int tipoUsuario) {
        super(nombre, correo, tipoUsuario);
        this.Id = Id;
        inventario = new ArrayList();
    }

    public Administrador() {
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

    public int getId() {
        return Id;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    @Override
    public String toString() {
        return "Administrador{" + "nombre=" + super.getNombre() + ", correo=" + super.getCorreo() + ", tipoUsuario=" + super.getTipoUsuario() + ", prestamos=" + super.getPrestamos() + "inventario=" + inventario + '}';
    }
    
    public void guardarDatos() throws IOException {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(new File("C:\\Users\\Green\\Documents\\NetBeansProjects\\Presta\\usuariosBase.csv"), true);
            pw = new PrintWriter(fichero);
                String linea = getId() + "," + getNombre() + "," + getCorreo() + "," + getTipoUsuario();
                pw.println(linea);
        } catch (IOException ex) {
        } finally {
            try {
                if (fichero != null) {
                    fichero.close();
                }
            } catch (IOException e) {
            }
        }
    }
      
}
