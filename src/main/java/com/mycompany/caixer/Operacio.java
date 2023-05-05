/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caixer;

/**
 *
 * @author JAN
 */
public class Operacio {
    private String fecha;
    private String email;
    private String tipo;
    private double importe;

    public Operacio(String fecha, String email, String tipo, double importe) {
        this.fecha = fecha;
        this.email = email;
        this.tipo = tipo;
        this.importe = importe;
    }

    public String getFecha() {
        return fecha;
    }

    public String getEmail() {
        return email;
    }

    public String getTipo() {
        return tipo;
    }

    public double getImporte() {
        return importe;
    }

    @Override
    public String toString() {
        return fecha + " - " + tipo + ": " + importe;
    }
}
