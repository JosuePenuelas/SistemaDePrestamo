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
}
