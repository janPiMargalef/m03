/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caixer;

import com.mycompany.caixer.Compte.TipusCompte;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
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
public class PerfilController {
private String email;
private ArrayList<Compte> comptes = new ArrayList<>();
@FXML
private ComboBox<TipusCompte> TipusCompteComboBox;
@FXML
private ListView<Compte> ComptesListView;
@FXML
TextField SaldoTextField;
@FXML
TextField TitularTextField;
@FXML
TextField TargetaTextField;
@FXML
DatePicker dataExpiracioPicker;
@FXML
Button switchToMenu;
@FXML
Label missatge;
@FXML
private TextField contrasenyaTextField;
@FXML
Label emailPerfil;
@FXML
private TextField novaContrasenyaTextField;
@FXML
private TextField confirmaContrasenyaTextField;
@FXML
Label missatgePass;
@FXML
private ListView<Targeta> targetesListView;
private ObservableList<Targeta> targetesObservableList = FXCollections.observableArrayList();


public void cargarComptesDeUsuari() {
      System.out.println("Cridant a cargarComptesDeUsuari()");
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
        ComptesListView.setItems(comptesObservableList);
    } catch (IOException e) {
        System.err.println("Error en comptes.csv: " + e.getMessage());
    }
}

public void initData(String Email) {
email = Email;
cargarComptesDeUsuari(); 
cargarTargetes();
emailPerfil.setText(email);
}

@FXML
public void initialize() {
   TipusCompteComboBox.getItems().addAll(TipusCompte.CORRIENTE, TipusCompte.AHORROS);
    ObservableList<Compte> comptesObservable = FXCollections.observableArrayList(comptes);
    ComptesListView.setItems(comptesObservable);
    
     targetesListView.setItems(targetesObservableList);

}
    
@FXML
public void tancarSessio() {
    try {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("Iniciar.fxml")); 
        Parent root = loader.load();
        Scene scene = new Scene(root);
        Stage stage = (Stage) ComptesListView.getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    } catch (IOException e) {
        System.err.println("Error carregant Iniciar.fxml: " + e.getMessage());
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
private void canviarContrasenya(ActionEvent event) {
    String novaContrasenya = novaContrasenyaTextField.getText();
    String confirmaContrasenya = confirmaContrasenyaTextField.getText();

    if (novaContrasenya.length() < 8) {
        missatgePass.setText("La contrasenya ha de tenir almenys 8 caràcters.");
        return;
    }

    if (!novaContrasenya.equals(confirmaContrasenya)) {
        missatgePass.setText("Les contrasenyes no coincideixen.");
        return;
    }

    List<String[]> users = readCsvFile("data/usuaris.csv");
    for (String[] user : users) {
        if (user[0].equals(email)) {
            user[1] = novaContrasenya;
            writeCsvFile(users, "data/usuaris.csv");
            missatgePass.setText("Contrasenya canviada correctament.");
            novaContrasenyaTextField.clear();
            confirmaContrasenyaTextField.clear();
            return;
        }
    }
    missatgePass.setText("No s'ha trobat l'email.");
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

private void writeCsvFile(List<String[]> content, String filePath) {
    try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
            for (String[] line : content) {
                writer.write(String.join(",", line));
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
@FXML
public void crearCompte() {
    if (comptes.size() < 10) {
        String titular = TitularTextField.getText();
        double saldo = Double.parseDouble(SaldoTextField.getText());
        TipusCompte tipusDeCompte = TipusCompteComboBox.getValue();
        long numeroCompte = ThreadLocalRandom.current().nextLong(1_000_000_000L,10_000_000_000L); 

        Compte compte = new Compte(numeroCompte, titular, saldo, tipusDeCompte); 
        comptes.add(compte);

        try (PrintWriter writer = new PrintWriter(new FileOutputStream(new File("data/comptes.csv"), true))) { 
            StringBuilder sb = new StringBuilder();
            sb.append(email + ",");
            sb.append(String.valueOf(numeroCompte) + ",");
            sb.append(titular + ",");
            sb.append(String.valueOf(saldo) + ",");
            sb.append(tipusDeCompte.name() + ",");
            sb.append("false"); 
            
            writer.write(sb.toString() + System.lineSeparator()); 
            missatge.setText("Compte creat correctament");
           
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        cargarComptesDeUsuari(); 
        TitularTextField.clear();
        SaldoTextField.clear();
        TipusCompteComboBox.getSelectionModel().clearSelection();
    } else {
       missatge.setText("No es pot crear més de 10 comptes.");
    }
}
@FXML
public void eliminarCompte() {
    Compte compteSeleccionat = ComptesListView.getSelectionModel().getSelectedItem();

    if (compteSeleccionat != null && compteSeleccionat.getSaldo() == 0) {
        String contrasenya = contrasenyaTextField.getText();

        if (contrasenyaValida(contrasenya)) {
            esborrarCompteDelFitxerCSV(compteSeleccionat);
            comptes.remove(compteSeleccionat);
            ComptesListView.setItems(FXCollections.observableArrayList(comptes));
        } else {
             missatge.setText("Contrasenya Incorrecta");
        }
    } else {
       missatge.setText("No s'ha seleccionat cap compte per eliminar o el compte seleccionat té saldo.");
    }
}


private void esborrarCompteDelFitxerCSV(Compte compteSeleccionat) {
    File inputFile = new File("data/comptes.csv");
    File tempFile = new File("data/myTempFile.csv");

    try {
        BufferedReader reader = new BufferedReader(new FileReader(inputFile));
        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

        String lineToRemove = email + "," + compteSeleccionat.getNumeroCompte() + "," + compteSeleccionat.getTitularCompte() + "," +
                              compteSeleccionat.getSaldo() + "," + compteSeleccionat.getTipusCompte().name() + ",false";
        String currentLine;

        while((currentLine = reader.readLine()) != null) {
            String trimmedLine = currentLine.trim();
            if(trimmedLine.equals(lineToRemove)) continue;
            writer.write(currentLine + System.getProperty("line.separator"));
        }
        writer.close(); 
        reader.close(); 

        if (!inputFile.delete()) {
            System.out.println("No s'ha pogut eliminar el fitxer original");
        }

        if (!tempFile.renameTo(inputFile))
            System.out.println("No s'ha pogut renombrar el fitxer");

    } catch (IOException ex) {
        ex.printStackTrace();
    }
}

private boolean contrasenyaValida(String contrasenya) {
    String projectDirectory = System.getProperty("user.dir");
    File directory = new File(projectDirectory + File.separator + "data");
    File file = new File(directory, "usuaris.csv");
    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(",");
            if (parts.length >= 3 && parts[0].equals(email) && parts[1].equals(contrasenya)) {
                return true;
            }
        }
    } catch (IOException e) {
        System.err.println("Error al llegir el arxiu usuaris.csv: " + e.getMessage());
    }
    return false; 
}
public Compte obtenirComptePerNumero(long numeroCompte) {
    System.out.println("Comprovant " + comptes.size() + " comptes");
    for (Compte compte : comptes) {
        System.out.println("Compte número: " + compte.getNumeroCompte());
        if (compte.getNumeroCompte() == numeroCompte) {
            return compte;
        }
    }
    return null;
}


public void cargarTargetes() {
    String projectDirectory = System.getProperty("user.dir");
    File directory = new File(projectDirectory + File.separator + "data");
    File file = new File(directory, "targetes.csv");

    try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
        String line;
        while ((line = reader.readLine()) != null) {
            // Imprimeix la línia que estàs llegint
            System.out.println("Llegint línia: " + line);
            
            String[] parts = line.split(",");
            if (parts.length >= 4) {
                long numeroCompte = Long.parseLong(parts[0]);
                long numeroTarjeta = Long.parseLong(parts[1]);
                String fechaExpiracion = parts[2];
                int cvv = Integer.parseInt(parts[3]);

                // Necessitaràs un mètode per obtenir un compte a partir del seu número.
               System.out.println("Buscant compte amb el número: " + numeroCompte);
Compte compte = obtenirComptePerNumero(numeroCompte);

                if (compte != null) {
                    Targeta targeta = new Targeta(compte, numeroTarjeta, fechaExpiracion, cvv);
                    System.out.println("Targeta creada: " + targeta);  // Imprimeix la targeta que acabes de crear
                     targetesObservableList.add(targeta);
                    System.out.println("Llista de targetes: " + targetesObservableList);  // Imprimeix la llista de targetes després d'afegir cada targeta
                } else {
                    System.out.println("No s'ha trobat cap compte amb el número: " + numeroCompte);  // Si compte és null, imprimeix aquest missatge
                }
            }
            
        }
    } catch (IOException e) {
        System.err.println("Error en targetes.csv: " + e.getMessage());
    }
}


@FXML
public void crearTargeta() {
    Compte compteSeleccionat = ComptesListView.getSelectionModel().getSelectedItem();

    if (compteSeleccionat != null && compteSeleccionat.getTipusCompte() == Compte.TipusCompte.CORRIENTE) {
        LocalDate fechaExpiracion = dataExpiracioPicker.getValue();
        long numeroTarjeta = ThreadLocalRandom.current().nextLong(1_000_000_000_000_000L, 10_000_000_000_000_000L);
        int cvv = ThreadLocalRandom.current().nextInt(100, 1000);

        Targeta targeta = new Targeta(compteSeleccionat, numeroTarjeta, fechaExpiracion.toString(), cvv);
        targetesObservableList.add(targeta);

        try (PrintWriter writer = new PrintWriter(new FileOutputStream(new File("data/targetes.csv"), true))) {
    StringBuilder sb = new StringBuilder();
    sb.append(compteSeleccionat.getNumeroCompte() + ",");
    sb.append(String.valueOf(targeta.getNumeroTarjeta()) + ",");
    sb.append(fechaExpiracion + ",");
    sb.append(String.valueOf(targeta.getCvv()));

    writer.write(sb.toString() + System.lineSeparator());
    
    // Afegit un missatge per indicar que la targeta s'ha creat correctament
    missatge.setText("Targeta creada correctament");
} catch (IOException e) {
    System.err.println("Error al escriure a l'arxiu targetes.csv: " + e.getMessage());
}
        targetesListView.setItems(FXCollections.observableArrayList(targetesObservableList));
    } else {
        missatge.setText("El compte associat a la targeta ha de ser corrent.");
    }
}


}
