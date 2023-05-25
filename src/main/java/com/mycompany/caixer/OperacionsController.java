/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caixer;

/**
 *
 * @author JAN
 */
import com.mycompany.caixer.Compte.TipusCompte;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javafx.fxml.Initializable;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.*;
import java.util.*;

public class OperacionsController implements Initializable {
    
private List<Bitllet> bitllets;

@FXML
private TextField quantitatTextField;
@FXML
Label missatge;    
@FXML
private ComboBox<Compte> comptesComboBox;

private String email;

private List<Compte> comptes = new ArrayList<>();
@FXML
Button switchToMenu;
@FXML
private TextField Bitllet5, Bitllet10, Bitllet20, Bitllet50, Bitllet100, Bitllet200, Bitllet500;

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
        comptesComboBox.setItems(comptesObservableList);
    } catch (IOException e) {
        System.err.println("Error en comptes.csv: " + e.getMessage());
    }
}

@Override
public void initialize(URL location, ResourceBundle resources) {
    String projectDirectory = System.getProperty("user.dir");
    File directory = new File(projectDirectory + File.separator + "data");
    File bitlletsFile = new File(directory, "bitllets.csv");

    bitllets = Bitllet.carregarBitllets(bitlletsFile.getAbsolutePath());  
}
@FXML
private void onDepositar() {
    
}


@FXML
private void onRetirar() {

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
}
