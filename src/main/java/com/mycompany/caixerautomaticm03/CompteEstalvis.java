/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caixerautomaticm03;

/**
 *
 * @author alumne
 */
public class CompteEstalvis extends Compte {
    private double tasaInteres;

    public CompteEstalvis(long numeroCompte, double saldo, String titularCompte) {
        super(numeroCompte ,titularCompte, saldo);
        this.tasaInteres = 0.0;
    }
    public void establirTasaInteres(double tasa) { //interes pel compte d'estalvi
        tasaInteres = tasa;
    }
}
