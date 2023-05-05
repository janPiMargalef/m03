/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caixer;

/**
 *
 * @author JAN
 */
import java.time.LocalDate;

public class Targeta {
    private Compte compte;
    private long numeroTarjeta;
    private String fechaExpiracion;
    private int cvv;

    public Targeta(Compte compte, long numeroTarjeta, String fechaExpiracion, int cvv) {
        this.compte = compte;
        this.numeroTarjeta = numeroTarjeta;
        this.fechaExpiracion = fechaExpiracion;
        this.cvv = cvv;
    }

    
    
    public Compte getCompte() {
        return compte;
    }

    public long getNumeroTarjeta() {
        return numeroTarjeta;
    }

    public String getFechaExpiracion() {
        return fechaExpiracion;
    }

    public int getCvv() {
        return cvv;
    }
    @Override
public String toString() {
    return "Targeta: " + numeroTarjeta + ", Titular: " + compte.getTitularCompte() + ", Fecha de expiración: " + fechaExpiracion + ", CVV: " + cvv;

}

}
