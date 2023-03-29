/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caixerautomaticm03;

/**
 *
 * @author alumne
 */
public class CompteCorrent extends Compte {
    private double sobregir;

    public CompteCorrent(String numeroCompte, double saldo, String titularCompte) {
        super(numeroCompte, "Compte Corrent",titularCompte, saldo);
        this.sobregir = 0;
    }

    //getter i setters

    public void establirSobregir(double quantitat) { //interes pel compte corrent seguent pas crear clase dins de caixer per crear compte..despres mostrarlo...
        sobregir = quantitat;
    }
}