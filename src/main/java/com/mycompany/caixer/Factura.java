/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caixer;

import java.time.LocalDate;

/**
 *
 * @author JAN
 */
public class Factura {
    
private String companyia;
private Long numeroCompte;
private Double importe;
private LocalDate data;    

public Factura(String companyia, Long numeroCompte, Double importe, LocalDate data) {
    this.companyia = companyia;
    this.numeroCompte = numeroCompte;
    this.importe = importe;
    this.data = data;
}

    public String getCompanyia() {
        return companyia;
    }

    public void setCompanyia(String companyia) {
        this.companyia = companyia;
    }

    public Long getNumeroCompte() {
        return numeroCompte;
    }

    public void setNumeroCompte(Long numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }
@Override
public String toString() {
    return "Factura{" +
            "companyia ='" + companyia + '\'' +
            ", numeroCompte =" + numeroCompte +
            ", import =" + importe +
            ", data =" + data +
            '}';
}


}
