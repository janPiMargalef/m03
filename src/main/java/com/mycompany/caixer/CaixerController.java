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
import static java.lang.Math.random;
import static java.lang.StrictMath.random;
import java.time.LocalDate;
import java.util.Random;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class CaixerController {
     @FXML
    private TextField titularTextField;
    @FXML
    private DatePicker dataExpiracioPicker;
    @FXML
    private Button crearTargeta;
    
    @FXML
    private ListView<Targeta> targetesListView;

    
    private long numeroCuentaSeleccionada;
    private ObservableList<Targeta> targeta;
    
    @FXML
    private TextField accountNumberTextField;

    @FXML
    private TextField accountHolderTextField;

    @FXML
    private TextField initialBalanceTextField;

    @FXML
    private Button createAccountButton;
    
    @FXML
    private ComboBox<Compte.TipusCompte> accountTypeComboBox;

    @FXML
    private ListView<Compte> accountsListView;

    private ObservableList<Compte> comptes;
 
     
 
@FXML
    public void initialize() {
        accountTypeComboBox.setItems(FXCollections.observableArrayList(Compte.TipusCompte.values()));
        comptes = FXCollections.observableArrayList();
        accountsListView.setItems(comptes);

        createAccountButton.setOnAction(event -> {
            if (comptes.size() < 10) {
                Random rand = new Random();
                long numeroCompte = 1_000_000_000L + Math.abs(rand.nextLong() % 9_000_000_000L);
                String titularCompte = accountHolderTextField.getText();
                double saldo = Double.parseDouble(initialBalanceTextField.getText());
                Compte.TipusCompte selectedAccountType = accountTypeComboBox.getValue();

                Compte compte = new Compte(numeroCompte, titularCompte, saldo, selectedAccountType);
                comptes.add(compte);

                guardarCuentaEnCSV(compte);

                accountNumberTextField.clear();
                accountHolderTextField.clear();
                initialBalanceTextField.clear();
                accountTypeComboBox.getSelectionModel().clearSelection();
            } else {
                System.out.println("No es poden crear més comptes. Límit de 10 comptes.");
            }
        });
         accountsListView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
        if (newValue != null) {
            numeroCuentaSeleccionada = newValue.getNumeroCompte();
           
        }
    });
           cargarTarjetasDeUsuario();
         targeta = FXCollections.observableArrayList();
targetesListView.setItems(targeta);
 
    }
    
    
private String userEmail;

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

public void cargarComptesDeUsuari() {
    String projectDirectory = System.getProperty("user.dir");
    File directory = new File(projectDirectory + File.separator + "data");
    File file = new File(directory, "comptes.csv");

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 6 && parts[0].equals(userEmail) && parts[5].equalsIgnoreCase("false")) {
                long numeroCompte = Long.parseLong(parts[1]);
                String titularCompte = parts[2];
                double saldo = Double.parseDouble(parts[3]);
                Compte.TipusCompte tipusCompte = Compte.TipusCompte.valueOf(parts[4]);

                Compte compte = new Compte(numeroCompte, titularCompte, saldo, tipusCompte);
                comptes.add(compte);
            }
        }
    } catch (IOException e) {
        System.err.println("error en comptes.csv: " + e.getMessage());
    }
}



   private void guardarCuentaEnCSV(Compte compte) {
    String projectDirectory = System.getProperty("user.dir");
    File directory = new File(projectDirectory + File.separator + "data");
    if (!directory.exists()) {
        directory.mkdir();
    }

    File file = new File(directory, "comptes.csv");

    try (FileWriter writer = new FileWriter(file, true)) {
        String cuentaCSV = String.join(",", userEmail, String.valueOf(compte.getNumeroCompte()), compte.getTitularCompte(),
                String.valueOf(compte.getSaldo()), compte.getTipusCompte().toString(), "false") + "\n";
        writer.write(cuentaCSV);
    } catch (IOException e) {
        System.err.println("error en CSV: " + e.getMessage());
    }
}

    
    
 public void switchToOperacions(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("Operacions.fxml"));
    Parent operacionsParent = loader.load();

    OperacionsController operacionsController = loader.getController();
    operacionsController.initData(userEmail);

    Scene operacionsScene = new Scene(operacionsParent);
    Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
    window.setScene(operacionsScene);
    window.show();
}
 
 


public void cargarTarjetasDeUsuario() {
    String projectDirectory = System.getProperty("user.dir");
    File directory = new File(projectDirectory + File.separator + "data");
    File file = new File(directory, "targetes.csv");

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 4) {
                long numeroCompte = Long.parseLong(parts[0]);

                // Verificar si el número de cuenta pertenece a alguna cuenta del usuario actual
                boolean cuentaPerteneceAlUsuario = comptes.stream().anyMatch(compte -> compte.getNumeroCompte() == numeroCompte);

                if (!cuentaPerteneceAlUsuario) {
                    continue;
                }

                // ... (resto del código existente en cargarTarjetasDeUsuario) ...
            }
        }
    } catch (IOException e) {
        System.err.println("Error al leer el archivo targetes.csv: " + e.getMessage());
    }
}


private Random random = new Random();

private long generarNumeroTarjetaAleatorio() {
    // Genera un número de tarjeta aleatorio de 16 dígitos
    return 1_0000_0000_0000_0000L + (long) (Math.random() * 9_0000_0000_0000_0000L);
}

private int generarCodigoSeguridadAleatorio() {
    // Genera un código de seguridad aleatorio de 3 dígitos
    return 100 + (int) (Math.random() * 900);
}

private void guardarTarjetaEnCSV(Targeta tarjeta) {
    String projectDirectory = System.getProperty("user.dir");
    File directory = new File(projectDirectory + File.separator + "data");
    File tarjetasFile = new File(directory, "targetes.csv");

    try (FileWriter fileWriter = new FileWriter(tarjetasFile, true);
         BufferedWriter writer = new BufferedWriter(fileWriter)) {

        String line = tarjeta.getCompte().getNumeroCompte() + "," +
                      tarjeta.getNumeroTarjeta() + "," +
                      tarjeta.getFechaExpiracion() + "," +
                      tarjeta.getCvv() + "\n";
        writer.write(line);

    } catch (IOException e) {
        System.err.println("Error al guardar la tarjeta en el archivo tarjetas.csv: " + e.getMessage());
    }
}
public void crearTarjetaAccion(ActionEvent event) {
    long numeroTarjeta = generarNumeroTarjetaAleatorio();
    String titular = titularTextField.getText();
    LocalDate fechaExpiracion = dataExpiracioPicker.getValue();
    int codigoSeguridad = generarCodigoSeguridadAleatorio();

    // Buscar el objeto 'Compte' correspondiente al número de cuenta seleccionado
    Compte compteSeleccionado = null;
    for (Compte compte : comptes) {
        if (compte.getNumeroCompte() == numeroCuentaSeleccionada) {
            compteSeleccionado = compte;
            break;
        }
    }

    if (compteSeleccionado != null) {
        Targeta nuevaTarjeta = new Targeta(compteSeleccionado, numeroTarjeta, fechaExpiracion.toString(), codigoSeguridad);
        guardarTarjetaEnCSV(nuevaTarjeta);

        targeta.add(nuevaTarjeta); // Agrega la nueva tarjeta a la lista observable
    } else {
        System.err.println("No se pudo encontrar la cuenta seleccionada.");
    }
}
}