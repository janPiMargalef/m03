/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caixerautomaticm03;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Date;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;

/**
 *
 * @author alumne
 */
public class Compte {
    private String numeroCompte;
    private String titularCompte;
    private String tipoCompte;
    private double saldo;

    public Compte(String numeroCompte, String titularCompte, String tipoCompte, double saldo) {
        this.numeroCompte = titularCompte;
        this.titularCompte = 
        this.tipoCompte = tipoCompte;
        this.saldo = saldo;
        
    }

    //getters i setters

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(String numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public String getTitularCompte() {
        return titularCompte;
    }

    public void setTitularCompte(String titularCompte) {
        this.titularCompte = titularCompte;
    }

    public String getTipoCompte() {
        return tipoCompte;
    }

    public void setTipoCompte(String tipoCompte) {
        this.tipoCompte = tipoCompte;
    }

    public double getSaldo() {
        return saldo;
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
}

