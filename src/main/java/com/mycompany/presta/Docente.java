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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Green
 */
public class Docente extends Usuario {

    private int numEmpleado;

    public Docente(int numEmpleado, String nombre, String correo, int tipoUsuario) {
        super(nombre, correo, tipoUsuario);
        this.numEmpleado = numEmpleado;
    }

    public Docente() {
        
    }

    public int getNumEmpleado() {
        return numEmpleado;
    }

    public void setNumEmpleado(int numEmpleado) {
        this.numEmpleado = numEmpleado;
    }

    @Override
    public String toString() {
        return "Docente{" + "nombre=" + super.getNombre() + ", correo=" + super.getCorreo() + ", tipoUsuario=" + super.getTipoUsuario() + "numEmpleado=" + numEmpleado + '}';
    }
    
    public void guardarDatos() throws IOException {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(new File("C:\\Users\\Green\\Documents\\NetBeansProjects\\Presta\\usuariosBase.csv"), true);
            pw = new PrintWriter(fichero);
                String linea = getNumEmpleado() + "," + getNombre() + "," + getCorreo() + "," + getTipoUsuario();
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
