/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.presta;

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
        return "Alumno{" + "matricula=" + matricula + ", tutor=" + tutor + '}';
    }

}
