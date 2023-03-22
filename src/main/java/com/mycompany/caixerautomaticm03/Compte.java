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
String numero;
protected LocalDateTime dataDiaEntrada;
double saldo;
Date dataCreacio;

    public LocalDateTime getDataDiaEntrada() {
        return dataDiaEntrada;
    }

    public void setDataDiaEntrada(LocalDateTime dataDiaEntrada) {
        this.dataDiaEntrada = dataDiaEntrada;
    }


 

    
    
    

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
    public double calcularInteres(int interes){//int interes es el %  //compte corrent -> 8%      compte estalvi -> 4% ?
        double resta = 0;
        double mitjanaSaldo = 0;
        LocalDateTime dataHoraActual = LocalDateTime.now();
        int hourOfDay = dataHoraActual.getHour();
        Duration durada = Duration.between(this.dataDiaEntrada,dataHoraActual);
        if(durada.equals(730))//calcular la mitjana del saldo després d'un mes
        {
             
            mitjanaSaldo = saldo + mitjanaSaldo;
        }
        if(durada.equals(8760))//calcular la resta després d'un any
        {
            
            interes = interes/12; //interes mensual
            resta = interes * mitjanaSaldo;
            saldo = saldo - resta;
        }
       
      return saldo;
    } 
}
