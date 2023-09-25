/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.presta;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Green
 */
public class Tutor {

    private String nombre;
    private String correo;

    public Tutor(String nombre, String correo) {
        this.nombre = nombre;
        this.correo = correo;
    }

    public Tutor() {
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String toString() {
        return "Tutor{" + "nombre=" + nombre + ", corre=" + correo + '}';
    }

    public void guardarDatos(String nombre) throws IOException {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(new File(nombre), true);
            pw = new PrintWriter(fichero);
                String linea = getNombre() + "," + getCorreo();
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
