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
import java.text.ParseException;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

/**
 *
 * @author Green
 */
public class Usuario {

    private String nombre;
    private String correo;
    private int tipoUsuario; //0. Administrador, 1. EncargadoDeCaseta, 2. Docente, 3. Alumno
    private ArrayList<Material> prestamos;

    public Usuario(String nombre, String correo, int tipoUsuario) {
        this.nombre = nombre;
        this.correo = correo;
        this.tipoUsuario = tipoUsuario;
        prestamos = new ArrayList<>();
    }

    public Usuario() {
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

    public int getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(int tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public ArrayList<Material> getPrestamos() {
        return prestamos;
    }

    public void setPrestamos(ArrayList<Material> prestamos) {
        this.prestamos = prestamos;
    }

    @Override
    public String toString() {
        return "Usuario{" + "nombre=" + nombre + ", correo=" + correo + ", tipoUsuario=" + tipoUsuario + ", prestamos=" + prestamos + '}';
    }
    
    public String getTipoUsuarioString(){
        String usuarioTipo = "";
        switch(tipoUsuario){
            case 0 -> usuarioTipo = "Administrador";
            
            case 1 -> usuarioTipo = "Encargado De Caseta";
                
            case 2 -> usuarioTipo = "Docente";
            
            case 3 -> usuarioTipo = "Alumno";
        }
        return usuarioTipo;
    }
    
    public void cargarPrestamos(String nombre) throws IOException, ParseException {
        FileReader fr = null;
        BufferedReader br = null;
        try {
            fr = new FileReader(new File(nombre));
            br = new BufferedReader(fr);
            String linea;
            while ((linea = br.readLine()) != null) {
                String arreglo[] = linea.split(",");
                Material m = new Material(arreglo[0], arreglo[1], arreglo[2]);
                SimpleDateFormat sdf = new SimpleDateFormat(arreglo[3]);
                Date date = sdf.parse(arreglo[3]);
                m.setFechaPedido(date);
                m.setFueDevuelto(Boolean.parseBoolean(arreglo[4]));
                prestamos.add(m);
            }
        } catch (IOException | NumberFormatException ex) {
        } finally {
            try {
                if (fr != null) {
                    fr.close();
                }
            } catch (IOException ex) {
            }
        }
    }
    
    public void guardarPrestamo(String nombre, Material material) throws IOException {
        FileWriter fichero = null;
        PrintWriter pw = null;
        try {
            fichero = new FileWriter(new File(nombre), true);
            pw = new PrintWriter(fichero);
            String linea = material.getNombre() + "," + material.getDescripcion() + "," + material.getPotenciador() + "," + material.getFechaPedido() + "," + material.isFueDevuelto();
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
