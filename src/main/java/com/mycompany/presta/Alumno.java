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
public class Alumno extends Usuario {

    private int matricula;
    private Tutor tutor;

    public Alumno(int matricula, Tutor tutor, String nombre, String correo, int tipoUsuario) {
        super(nombre, correo, tipoUsuario);
        this.matricula = matricula;
        this.tutor = tutor;
    }

    public Alumno() {
        
    }

    public Alumno(int matricula, Tutor tutor) {
        this.matricula = matricula;
        this.tutor = tutor;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    @Override
    public String toString() {
        return "Alumno{" + "nombre=" + super.getNombre() + ", correo=" + super.getCorreo() + ", tipoUsuario=" + super.getTipoUsuario() + "matricula=" + matricula + ", tutor=" + tutor + '}';
    }
    
    public void guardarDatos(String nombre) throws IOException {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(new File(nombre), true);
            pw = new PrintWriter(fichero);
                String linea = getMatricula() + "," + getNombre() + "," + getCorreo() + "," + tutor.getCorreo() + "," + getTipoUsuario();
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
