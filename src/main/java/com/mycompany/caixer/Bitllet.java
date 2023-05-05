/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caixer;

/**
 *
 * @author JAN
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Bitllet {
    private int denominacio;
    private int quantitat;

    public Bitllet(int denominacio, int quantitat) {
        this.denominacio = denominacio;
        this.quantitat = quantitat;
    }

    public int getDenominacio() {
        return denominacio;
    }

    public void setDenominacio(int denominacio) {
        this.denominacio = denominacio;
    }

    public int getQuantitat() {
        return quantitat;
    }

    public void setQuantitat(int quantitat) {
        this.quantitat = quantitat;
    }

    public static List<Bitllet> carregarBitllets(String csvFile) {
    List<Bitllet> bitllets = new ArrayList<>();
    String line;
    try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
        while ((line = br.readLine()) != null) {
            String[] values = line.split(",");
            int denominacio = Integer.parseInt(values[0]);
            int quantitat = Integer.parseInt(values[1]);
            bitllets.add(new Bitllet(denominacio, quantitat));
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
    return bitllets;
}

}
