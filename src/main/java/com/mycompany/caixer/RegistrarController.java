/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caixer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author JAN
 */
public class RegistrarController {
@FXML
private TextField emailTextField;
@FXML
private TextField nomTextField;
@FXML
private PasswordField passwordTextField;
@FXML
Label missatge;

@FXML
    private void registrarUsuari(ActionEvent event) {
        String email = emailTextField.getText();
        String nom = nomTextField.getText();
        String password = passwordTextField.getText();

        if (password.length() < 8) {
            // Mostra un missatge d'error a l'usuari
            missatge.setText("La contrasenya ha de tenir almenys 8 caràcters.");
            return;
        }

        // Comprovar si l'email ja existeix
        List<String[]> users = readCsvFile("data/usuaris.csv");
        for (String[] user : users) {
            if (user[0].equals(email)) {
               missatge.setText("Aquest email ja existeix.");
                return;
            }
        }

        String csvContent = email + "," + password + "," + nom + ",," + "\n";
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("data/usuaris.csv", true));
            writer.append(csvContent);
            writer.close();
            SwitchToIniciar();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private List<String[]> readCsvFile(String filePath) {
        List<String[]> content = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = br.readLine()) != null) {
                content.add(line.split(","));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
@FXML
private void SwitchToIniciar() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Iniciar.fxml"));
            Stage stage = (Stage) emailTextField.getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
