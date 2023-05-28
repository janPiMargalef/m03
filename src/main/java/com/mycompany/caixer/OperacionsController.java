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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class OperacionsController implements Initializable {
    
private List<Bitllet> bitllets;
@FXML
Label missatgeOperacions;
@FXML
private TextField quantitatTextField;
@FXML
Label missatge;    
@FXML
private ComboBox<Compte> comptesComboBox;
private String email;
private TextField selectedTextField = null;
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
    
    Bitllet5.setOnMouseClicked(e -> selectedTextField = Bitllet5);
    Bitllet10.setOnMouseClicked(e -> selectedTextField = Bitllet10);
    Bitllet20.setOnMouseClicked(e -> selectedTextField = Bitllet20);
    Bitllet50.setOnMouseClicked(e -> selectedTextField = Bitllet50);
    Bitllet100.setOnMouseClicked(e -> selectedTextField = Bitllet100);
    Bitllet200.setOnMouseClicked(e -> selectedTextField = Bitllet200);
    Bitllet500.setOnMouseClicked(e -> selectedTextField = Bitllet500);
}
@FXML
private void onNumberButtonClicked(ActionEvent event) {
    if (selectedTextField != null) {
        Button clickedButton = (Button) event.getSource();
        String existingText = selectedTextField.getText();
        String newText = existingText + clickedButton.getText();
        selectedTextField.setText(newText);
    }
}
@FXML
private void onDeleteButtonClicked() {
    if (selectedTextField != null && !selectedTextField.getText().isEmpty()) {
        String existingText = selectedTextField.getText();
        String newText = existingText.substring(0, existingText.length() - 1);
        selectedTextField.setText(newText);
    }
}

 private static final String CSV_FILE = "data/bitllets.csv";

@FXML
private void onDepositar() {
    try {
        Compte compteSeleccionat = comptesComboBox.getValue();

        if (compteSeleccionat == null) {
            missatgeOperacions.setText("Si us plau, selecciona un compte abans de realitzar l'operació.");
            return;
        }
        
        Path path = Paths.get(CSV_FILE);
        List<String> lines = Files.readAllLines(path);
        Map<Integer, Integer> data = new HashMap<>();

        for (String line : lines) {
            String[] split = line.split(",");
            int key = Integer.parseInt(split[0]);
            int value = Integer.parseInt(split[1]);
            data.put(key, value);
        }

        int totalDepositat = 0;

         if (!Bitllet5.getText().isEmpty()) {
            int value = Integer.parseInt(Bitllet5.getText());
            totalDepositat += value * 5;
            data.computeIfPresent(5, (k, v) -> v + value);
        }
        if (!Bitllet10.getText().isEmpty()) {
            int value = Integer.parseInt(Bitllet10.getText());
            totalDepositat += value * 10;
            data.computeIfPresent(10, (k, v) -> v + value);
        }
        if (!Bitllet20.getText().isEmpty()) {
            int value = Integer.parseInt(Bitllet20.getText());
            totalDepositat += value * 20;
            data.computeIfPresent(20, (k, v) -> v + value);
        }
        if (!Bitllet50.getText().isEmpty()) {
            int value = Integer.parseInt(Bitllet50.getText());
            totalDepositat += value * 50;
            data.computeIfPresent(50, (k, v) -> v + value);
        }
        if (!Bitllet100.getText().isEmpty()) {
            int value = Integer.parseInt(Bitllet100.getText());
            totalDepositat += value * 100;
            data.computeIfPresent(100, (k, v) -> v + value);
        }
        if (!Bitllet200.getText().isEmpty()) {
            int value = Integer.parseInt(Bitllet200.getText());
            totalDepositat += value * 200;
            data.computeIfPresent(200, (k, v) -> v + value);
        }
        if (!Bitllet500.getText().isEmpty()) {
            int value = Integer.parseInt(Bitllet500.getText());
            totalDepositat += value * 500;
            data.computeIfPresent(500, (k, v) -> v + value);
        }
        
        if (totalDepositat > 2500) {
            missatgeOperacions.setText("No es pot dipositar més de 2500 euros a la vegada.");
            return;
        }
        
        FileWriter writer = new FileWriter(CSV_FILE, false);
        for (Map.Entry<Integer, Integer> entry : data.entrySet()) {
            writer.append(entry.getKey().toString());
            writer.append(",");
            writer.append(entry.getValue().toString());
            writer.append("\n");
        }

        writer.flush();
        writer.close();

        compteSeleccionat.setSaldo(compteSeleccionat.getSaldo() + totalDepositat);
        actualitzarSaldoEnCSV(compteSeleccionat);
        missatgeOperacions.setText("Dipòsit realitzat correctament.");
        
        Bitllet5.clear();
        Bitllet10.clear();
        Bitllet20.clear();
        Bitllet50.clear();
        Bitllet100.clear();
        Bitllet200.clear();
        Bitllet500.clear();
        cargarComptesDeUsuari();

    } catch (Exception e) {
        missatgeOperacions.setText("Hi ha hagut un error en l'operació.");
    }
}

@FXML
private void onRetirar() {
    try{
        Compte compteSeleccionat = comptesComboBox.getValue();

        if (compteSeleccionat == null) {
            missatgeOperacions.setText("Si us plau, selecciona un compte abans de realitzar l'operació.");
            return;
        }

        Path path = Paths.get(CSV_FILE);
        List<String> lines = Files.readAllLines(path);
        Map<Integer, Integer> data = new HashMap<>();

        for (String line : lines) {
            String[] split = line.split(",");
            int key = Integer.parseInt(split[0]);
            int value = Integer.parseInt(split[1]);
            data.put(key, value);
        }

        int totalRetirada = 0;
        boolean retiradaPossible = true;

    if (!Bitllet5.getText().isEmpty()) {
            int value = Integer.parseInt(Bitllet5.getText());
            totalRetirada += value * 5;
            data.computeIfPresent(5, (k, v) -> v - value);
        }
        if (!Bitllet10.getText().isEmpty()) {
            int value = Integer.parseInt(Bitllet10.getText());
            totalRetirada += value * 10;
            data.computeIfPresent(10, (k, v) -> v - value);
        }
        if (!Bitllet20.getText().isEmpty()) {
            int value = Integer.parseInt(Bitllet20.getText());
            totalRetirada += value * 20;
            data.computeIfPresent(20, (k, v) -> v - value);
        }
        if (!Bitllet50.getText().isEmpty()) {
            int value = Integer.parseInt(Bitllet50.getText());
            totalRetirada += value * 50;
            data.computeIfPresent(50, (k, v) -> v - value);
        }
        if (!Bitllet100.getText().isEmpty()) {
            int value = Integer.parseInt(Bitllet100.getText());
            totalRetirada += value * 100;
            data.computeIfPresent(100, (k, v) -> v - value);
        }
        if (!Bitllet200.getText().isEmpty()) {
            int value = Integer.parseInt(Bitllet200.getText());
            totalRetirada += value * 200;
            data.computeIfPresent(200, (k, v) -> v - value);
        }
        if (!Bitllet500.getText().isEmpty()) {
            int value = Integer.parseInt(Bitllet500.getText());
            totalRetirada += value * 500;
            data.computeIfPresent(500, (k, v) -> v - value);
        }

        for (Map.Entry<Integer, Integer> entry : data.entrySet()) {
            if (entry.getValue() < 0) {
                missatgeOperacions.setText("No hi ha més bitllets de "+entry.getKey()+"€"); 
                retiradaPossible = false;
                break;
            }
        }

        if (retiradaPossible) {
            FileWriter writer = new FileWriter(CSV_FILE, false);
            for (Map.Entry<Integer, Integer> entry : data.entrySet()) {
                writer.append(entry.getKey().toString());
                writer.append(",");
                writer.append(entry.getValue().toString());
                writer.append("\n");
            }

            writer.flush();
            writer.close();

            compteSeleccionat.setSaldo(compteSeleccionat.getSaldo() - totalRetirada);
            actualitzarSaldoEnCSV(compteSeleccionat);
            missatgeOperacions.setText("Retiració realitzada correctament.");
            
            Bitllet5.clear();
            Bitllet10.clear();
            Bitllet20.clear();
            Bitllet50.clear();
            Bitllet100.clear();
            Bitllet200.clear();
            Bitllet500.clear();
            cargarComptesDeUsuari();
        }
    } catch (Exception e) {
       missatgeOperacions.setText("Hi ha hagut un error en l'operació.");
    }
}


public void actualitzarSaldoEnCSV(Compte compteActualizado) {
    String projectDirectory = System.getProperty("user.dir");
    File directory = new File(projectDirectory + File.separator + "data");
    File file = new File(directory, "comptes.csv");
    List<String> lines = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 6 && Long.parseLong(parts[1]) == compteActualizado.getNumeroCompte()) {
                lines.add(String.join(",", parts[0], String.valueOf(compteActualizado.getNumeroCompte()),
                        compteActualizado.getTitularCompte(), String.valueOf(compteActualizado.getSaldo()),
                        compteActualizado.getTipusCompte().toString(), parts[5]));
            } else {
                lines.add(line);
            }
        }
    } catch (IOException e) {
        System.err.println("Error al llegir larxiu comptes.csv: " + e.getMessage());
    }

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
        for (String line : lines) {
            writer.write(line);
            writer.newLine();
        }
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
}