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
    private long numeroCompte;
    private String titularCompte;
    private double saldo;

    public Compte(long numeroCompte, String titularCompte, double saldo) {
        this.numeroCompte = numeroCompte;
        this.titularCompte = titularCompte;
        this.saldo = saldo;
        
    }

    //getters i setters + transferir

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
    
}

