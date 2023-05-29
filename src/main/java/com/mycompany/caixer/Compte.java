package com.mycompany.caixer;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
package com.mycompany.caixerautomatic;


/**
 *
 * @author alumne
 */
public class Compte {
    public enum TipusCompte {
    CORRIENTE,
    AHORROS
}
    
    private long numeroCompte;
    private String titularCompte;
    private double saldo;
    private TipusCompte tipusCompte;
    private double interes;
    private boolean tarjetaAsociada;

    public Compte(long numeroCompte, String titularCompte, double saldo, TipusCompte tipusCompte) {
        this.numeroCompte = numeroCompte;
        this.titularCompte = titularCompte;
        this.saldo = saldo;
        this.tipusCompte = tipusCompte;

        if (tipusCompte == TipusCompte.CORRIENTE) {
            interes = 0; 
            tarjetaAsociada = true;
        } else {
            interes = 0.02; 
            tarjetaAsociada = false;
        }
        
    }

    public TipusCompte getTipusCompte() {
        return tipusCompte;
    }

    public void setTipusCompte(TipusCompte tipusCompte) {
        this.tipusCompte = tipusCompte;
    }

    public double getInteres() {
        return interes;
    }

    public void setInteres(double interes) {
        this.interes = interes;
    }

    public boolean isTarjetaAsociada() {
        return tarjetaAsociada;
    }

    public void setTarjetaAsociada(boolean tarjetaAsociada) {
        this.tarjetaAsociada = tarjetaAsociada;
    }

    

    public long getNumeroCompte() {
        return numeroCompte;
    }

    

    public String getTitularCompte() {
        return titularCompte;
    }

 

    
    public double getSaldo() {
        return saldo;
    }

    public void setNumeroCompte(long numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public void setTitularCompte(String titularCompte) {
        this.titularCompte = titularCompte;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    

    
    public void depositar(double quantitat) {
        saldo += quantitat;
    }

    public void retirar(double quantitat) {
        saldo -= quantitat;
    }
    @Override
    public String toString() {
        return "Número de compte: " + numeroCompte + ", Titular: " + titularCompte + ", Saldo: " + saldo;
    }
}