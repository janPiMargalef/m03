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
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author JAN
 */
public class TransferirController { 
@FXML
private ListView<Compte> comptesListView;
@FXML
TextField quantitatTextField;
@FXML
TextField cuentaDestinoTextField;
@FXML
Label mensajeTransferenciaLabel;
@FXML
TextField compteDestiTextField;
@FXML
Button switchToMenu;
private String userEmail;
      private List<Compte> comptes = new ArrayList<>();

private String email;
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
        comptesListView.setItems(comptesObservableList);
    } catch (IOException e) {
        System.err.println("Error en comptes.csv: " + e.getMessage());
    }
}



 
public void initialize(URL location, ResourceBundle resources) {
    String projectDirectory = System.getProperty("user.dir");
    File directory = new File(projectDirectory + File.separator + "data");
    File bitlletsFile = new File(directory, "bitllets.csv");
}
public void handleButtonPress(ActionEvent event) { //cargar teclat
    Button button = (Button) event.getSource();
    String buttonText = button.getText();
    quantitatTextField.setText(quantitatTextField.getText() + buttonText);
}

@FXML
private void handleEliminar(ActionEvent event) { //eliminar numeros
    String text = quantitatTextField.getText();
    if (!text.isEmpty()) {
        quantitatTextField.setText(text.substring(0, text.length() - 1));
    }
}
public void actualizarSaldoEnCSV(Compte compteActualizado) {
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
private void realizarTransferencia() {
    Compte cuentaOrigen = comptesListView.getSelectionModel().getSelectedItem();
    long numeroCuentaOrigen = cuentaOrigen.getNumeroCompte();
    long numeroCuentaDestino;
    double importe;

    try {
        numeroCuentaDestino = Long.parseLong(compteDestiTextField.getText());
        importe = Double.parseDouble(quantitatTextField.getText());
    } catch (NumberFormatException e) {
        mensajeTransferenciaLabel.setText("Número de compte o import no vàlids.");
        return;
    }

    if (cuentaOrigen.getTipusCompte() != Compte.TipusCompte.CORRIENTE) {
        mensajeTransferenciaLabel.setText("Nomès es poden fer transferències a comptes corrents.");
        return;
    }

    Compte cuentaDestino = buscarCuentaPorNumero(numeroCuentaDestino);
    
    if (cuentaDestino == null) {
        mensajeTransferenciaLabel.setText("El compte destí no existeix.");
        return;
    }

    if (cuentaOrigen.getSaldo() >= importe) {
        cuentaOrigen.setSaldo(cuentaOrigen.getSaldo() - importe);
        cuentaDestino.setSaldo(cuentaDestino.getSaldo() + importe);
        actualizarSaldoEnCSV(cuentaOrigen);
        actualizarSaldoEnCSV(cuentaDestino);
        cargarComptesDeUsuari();
        quantitatTextField.clear();
        compteDestiTextField.clear();
       
        String emailCuentaDestino = obtenerEmailCuenta(numeroCuentaDestino);

      
        registrarOperacion(userEmail, "Transferencia de Sortida", importe);
        
        
        registrarOperacion(emailCuentaDestino, "Transferencia Entrant", importe);

        mensajeTransferenciaLabel.setText("Transferencia realitzada amb èxit!");
    } else {
        mensajeTransferenciaLabel.setText("Saldo insuficient en el compte bancari!");
    }
}

private String obtenerEmailCuenta(long numeroCuenta) {
    String projectDirectory = System.getProperty("user.dir");
    File directory = new File(projectDirectory + File.separator + "data");
    File file = new File(directory, "comptes.csv");

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 6 && Long.parseLong(parts[1]) == numeroCuenta) {
                return parts[0];
            }
        }
    } catch (IOException e) {
        System.err.println("Error en comptes.csv: " + e.getMessage());
    }

    return null;
}
private Compte buscarCuentaPorNumero(long numeroCuenta) {
    String projectDirectory = System.getProperty("user.dir");
    File directory = new File(projectDirectory + File.separator + "data");
    File file = new File(directory, "comptes.csv");

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 6 && Long.parseLong(parts[1]) == numeroCuenta) {
                long numeroCompte = Long.parseLong(parts[1]);
                String titularCompte = parts[2];
                double saldo = Double.parseDouble(parts[3]);
                Compte.TipusCompte tipusCompte = Compte.TipusCompte.valueOf(parts[4]);

                return new Compte(numeroCompte, titularCompte, saldo, tipusCompte);
            }
        }
    } catch (IOException e) {
        System.err.println("Error en comptes.csv: " + e.getMessage());
    }

    return null;
}

 private void registrarOperacion(String email, String tipoOperacion, double importe) {
    String projectDirectory = System.getProperty("user.dir");
    File directory = new File(projectDirectory + File.separator + "data");
    File file = new File(directory, "operacions.csv");

    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
        String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
        writer.write(timestamp + "," + email + "," + tipoOperacion + "," + importe);
        writer.newLine();
    } catch (IOException e) {
        System.err.println("Error en operacions.csv: " + e.getMessage());
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

