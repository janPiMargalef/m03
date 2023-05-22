/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caixer;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author JAN
 */
public class MenuController {
    
    private String email;

    public void setEmail(String email) {
        this.email = email;
    }

@FXML
private void switchToTransferir(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Transferir.fxml"));
        Parent transferirParent = loader.load();

        // Obtenim el controlador i inicialitzem les dades
        TransferirController transferirController = loader.getController();
        transferirController.initData(email);  // passa l'email a la nova escena

        Scene transferirScene = new Scene(transferirParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(transferirScene);
        window.show();
    } catch (IOException e) {
        System.err.println("Error al cargar Transferir.fxml: " + e.getMessage());
    }
}

@FXML
private void switchToOperacions(ActionEvent event) {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Operacions.fxml"));
        Parent transferirParent = loader.load();

        // Obtenim el controlador i inicialitzem les dades
        OperacionsController operacionsController = loader.getController();
        operacionsController.initData(email);

        Scene transferirScene = new Scene(transferirParent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(transferirScene);
        window.show();
    } catch (IOException e) {
        System.err.println("Error al cargar Transferir.fxml: " + e.getMessage());
    }
}

}
