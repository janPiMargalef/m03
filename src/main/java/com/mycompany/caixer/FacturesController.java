/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caixer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author JAN
 */
public class FacturesController {
    private String email;
private ArrayList<Compte> comptes = new ArrayList<>();
@FXML
Label missatge;
@FXML
Button switchToMenu;
@FXML
private ListView<String> facturesListView;
private ObservableList<String> facturesObservableList = FXCollections.observableArrayList();


@FXML
TextField companyiaTextField;
@FXML
TextField numeroTextField;
@FXML
TextField importTextField;
@FXML
DatePicker dataTextField;
@FXML
ComboBox numeroBox;
 private List<Factura> factures;
public void initData(String Email) {
email = Email;
cargarComptesDeUsuari();  

}
@FXML
public void initialize() {
facturesListView.setItems(facturesObservableList);   
factures = new ArrayList<>();
    cargarFactures();
    llistarFactures();
}
private void llistarFactures() {
    ObservableList<String> facturesObservableList = FXCollections.observableArrayList();
    for (Factura factura : factures) {
        facturesObservableList.add(factura.toString());  
    }
    facturesListView.setItems(facturesObservableList);
}



public void cargarComptesDeUsuari() {
    String projectDirectory = System.getProperty("user.dir");
    File directory = new File(projectDirectory + File.separator + "data");
    File file = new File(directory, "comptes.csv");

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        
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
        
        numeroBox.setItems(FXCollections.observableArrayList(comptes));
    } catch (IOException e) {
        System.err.println("Error en comptes.csv: " + e.getMessage());
    }
}
public void cargarFactures() {
    
    String projectDirectory = System.getProperty("user.dir");
    File directory = new File(projectDirectory + File.separator + "data");
    File file = new File(directory, "factures.csv");

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        reader.readLine();
        String line;
        
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 4) {
                String companyia = parts[0];
                Long numeroCompte = Long.parseLong(parts[1]);
                Double importe = Double.parseDouble(parts[2]);
                LocalDate data = LocalDate.parse(parts[3]);
                
                Factura factura = new Factura(companyia, numeroCompte, importe, data);
                factures.add(factura);
            }
        }
    } catch (IOException e) {
        System.err.println("Error en facturas.csv: " + e.getMessage());
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

@FXML
public void guardarFactura() {
String companyia = companyiaTextField.getText();
Compte compteSeleccionat = (Compte) numeroBox.getValue();
Long numeroCompteSeleccionat = compteSeleccionat.getNumeroCompte();

String importe = importTextField.getText();
LocalDate dataDate = dataTextField.getValue();

    if (companyia.isEmpty() || numeroCompteSeleccionat == null || importe.isEmpty() || dataDate == null) {
        missatge.setText("Tots els camps han d'estar emplenats.");
        return;
    }

    String numero = Long.toString(numeroCompteSeleccionat);
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    String data = dataDate.format(formatter);

    File file = new File("data/factures.csv");
    try (FileWriter fr = new FileWriter(file, true); 
         BufferedWriter br = new BufferedWriter(fr)) {
        String dataToAppend = "\n" + companyia + "," + numero + "," + importe + "," + data + "," + ""; 
        br.write(dataToAppend);
        companyiaTextField.clear();
        numeroBox.setValue(null); 
        importTextField.clear();
        dataTextField.setValue(null); 
        missatge.setText("Factura creada correctament.");
       
    
    } catch (IOException e) {
        e.printStackTrace();
    }
}



}
