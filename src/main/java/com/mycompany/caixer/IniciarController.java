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
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class IniciarController {
    @FXML
    Label missatge;
    @FXML
    private TextField emailTextField;

    @FXML
    private PasswordField passwordTextField;

    
    @FXML
    Button registrarseButton;
    
    private int intentsFallats = 0;

    
private void bloquejarCompte(String email) {  //bloquejar als 3 intents fallats
    String projectDirectory = System.getProperty("user.dir");
    File directory = new File(projectDirectory + File.separator + "data");
    File file = new File(directory, "usuaris.csv");

    List<String> lines = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 3 && parts[0].equals(email)) {
                parts[2] = "true"; // Canviar "Bloquejat" a true
            }
            lines.add(String.join(",", parts));
        }
    } catch (IOException e) {
        System.err.println("Error al llegir l'arxiu usuaris.csv: " + e.getMessage());
    }

    try (FileWriter writer = new FileWriter(file)) {
        for (String line : lines) {
            writer.write(line + "\n");
        }
    } catch (IOException e) {
        System.err.println("Error al actualitzar el arxiu suaris.csv: " + e.getMessage());
    }
}
    
    @FXML
    private void ferLogin(ActionEvent event) throws IOException {
        String email = emailTextField.getText();
        String password = passwordTextField.getText();

        if (credencialsValides(email, password)) { //comprobar que credencials és true

                intentsFallats = 0;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Menu.fxml"));
                Parent menuParent = loader.load();

                MenuController menuController = loader.getController();
                menuController.setEmail(email);
               
              

                Scene menuScene = new Scene(menuParent);
                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(menuScene);
                window.show();
           
        } else {
             intentsFallats++;
             missatge.setText("Credencials Incorrectes");
              if (intentsFallats >= 3) {
                missatge.setText("Compte bloquejat");
                bloquejarCompte(email);
                System.out.println("Compte bloquejat. Has fallat 3 vegades!");
            } else {      
                System.out.println("Credenciales incorrectas. Intento " + intentsFallats + " de 3.");
            }
        }
    }
   private boolean credencialsValides(String email, String password) {
    String projectDirectory = System.getProperty("user.dir");
    File directory = new File(projectDirectory + File.separator + "data");
    File file = new File(directory, "usuaris.csv");

   try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 3 && parts[0].equals(email) && parts[1].equals(password)) {
                if (parts[2].equalsIgnoreCase("true")) {
                    missatge.setText("El compte està bloquejat");
                    return false;
                } else {
                    return true;
                }
            }
        }
    } catch (IOException e) {
        System.err.println("Error al llegir el arxiu usuaris.csv: " + e.getMessage());
    }

    return false;
}
   
  @FXML
public void registrarUsuari() {
    try {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Registrar.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setScene(scene);
        stage.show();

        // Tanca la finestra actual si és necessari
        Stage currentStage = (Stage) registrarseButton.getScene().getWindow();
        currentStage.close();
    } catch (IOException e) {
        System.err.println("Error carregant Registrar.fxml: " + e.getMessage());
    }
}

}
