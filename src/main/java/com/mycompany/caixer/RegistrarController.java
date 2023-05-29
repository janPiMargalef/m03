/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caixer;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
        missatge.setText("La contrasenya ha de tenir almenys 8 caràcters.");
        return;
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
