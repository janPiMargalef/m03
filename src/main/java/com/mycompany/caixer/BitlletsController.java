/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caixer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author JAN
 */
public class BitlletsController {
private String email;
private ArrayList<Compte> comptes = new ArrayList<>();
@FXML   
TextField bitllet5;
@FXML   
TextField bitllet10;
@FXML 
TextField bitllet20;
@FXML 
TextField bitllet50;
@FXML 
TextField bitllet100;
@FXML 
TextField bitllet200;
@FXML 
TextField bitllet500;  
@FXML 
Button switchToMenu;

public void initData(String Email) {
email = Email;
cargarComptesDeUsuari();  
}

public void cargarComptesDeUsuari() {
    String projectDirectory = System.getProperty("user.dir");
    File directory = new File(projectDirectory + File.separator + "data");
    File file = new File(directory, "comptes.csv");

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        comptes.clear();
        while ((line = reader.readLine()) != null) {
            
            String[] parts = line.split(",");
            
            if (parts.length >= 6 && parts[0].equals(email) && parts[5].equalsIgnoreCase("false")) {
                long numeroCompte = Long.parseLong(parts[1]);
                String titularCompte = parts[2];
                double saldo = Double.parseDouble(parts[3]);
                Compte.TipusCompte tipusCompte = Compte.TipusCompte.valueOf(parts[4]);

                Compte compte = new Compte(numeroCompte, titularCompte, saldo, tipusCompte);
                comptes.add(compte);
                
            }
        }
        ObservableList<Compte> comptesObservableList = FXCollections.observableArrayList(comptes);
        
    } catch (IOException e) {
        System.err.println("Error en comptes.csv: " + e.getMessage());
    }
}   
@FXML
private void handleSwitchToMenu() {
    try {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("Menu.fxml"));
        Parent root = fxmlLoader.load();
        
        MenuController menuController = fxmlLoader.getController();
        menuController.setEmail(email);
        
        Scene scene = new Scene(root);
        Stage stage = (Stage) switchToMenu.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    } catch (IOException e) {
        System.err.println("Error en Menu.fxml: " + e.getMessage());
    }
}
public void initialize() {
    carregarBitllets();
}

private void carregarBitllets() {
    String projectDirectory = System.getProperty("user.dir");
    String filePath = projectDirectory + File.separator + "data" + File.separator + "bitllets.csv";

    try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 2) {
                String valorBitllet = parts[0];
                String quantitatBitllet = parts[1];
                switch (valorBitllet) {
                    case "5":
                        bitllet5.setText(quantitatBitllet);
                        break;
                    case "10":
                        bitllet10.setText(quantitatBitllet);
                        break;
                    case "20":
                        bitllet20.setText(quantitatBitllet);
                        break;
                    case "50":
                        bitllet50.setText(quantitatBitllet);
                        break;
                    case "100":
                        bitllet100.setText(quantitatBitllet);
                        break;
                    case "200":
                        bitllet200.setText(quantitatBitllet);
                        break;
                    case "500":
                        bitllet500.setText(quantitatBitllet);
                        break;
                }
            }
        }
    } catch (IOException e) {
        e.printStackTrace();
    }
}
}