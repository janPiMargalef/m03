/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caixerautomaticm03;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;
/**
 *
 * @author alumnea
 */
public class Caixer {
    
     @FXML
     TextField usernameTextField;
      @FXML
      TextField passwordTextField;
       @FXML
       Label missatge;
       @FXML
       Label nomUsuari;
       @FXML
       TextField numeroCompteTextField;
       @FXML
       TextField titularTextField;
       @FXML
       TextField saldoTextField;
       @FXML
       RadioButton compteCorrent;
       @FXML
       RadioButton compteEstalvis;
       
       
        //crear usuari
 public static ArrayList<Client> usuaris = new ArrayList<Client>();
 
 
       
//crear compte
    @FXML
   
    private long generarNumeroCompteAleatori() {
    Random rand = new Random();
    long numeroAleatori = (long) (rand.nextInt(900000000) + 1000000000);
    return numeroAleatori;
}
      
@FXML
private ListView<Compte> comptesListView;
private ObservableList<Compte> CompteList = FXCollections.observableArrayList();
    
  @FXML  
    private void afegirCompte() {
        
        
        if (CompteList.size() < 10) {
            
            long numeroCompte = generarNumeroCompteAleatori();
            Double Saldo = Double.parseDouble(saldoTextField.getText());    
            String titular = titularTextField.getText();
             Compte cuenta = new Compte(numeroCompte, titular, Saldo);
        CompteList.add(cuenta);
        
        comptesListView.refresh();
        
    }
    }
    
     public void initialize() {
      usuaris.add(new Client("prova1", "pass1"));
      comptesListView.setCellFactory(new Callback<ListView<Compte>, ListCell<Compte>>() {
    @Override
    public ListCell<Compte> call(ListView<Compte> param) {
        return new ListCell<>() {
            @Override
            protected void updateItem(Compte cuenta, boolean empty) {
                super.updateItem(cuenta, empty);
                if (cuenta != null) {
                    //setText(Compte.getNumeroCompte() + " - " + Saldo + " - " + titular); error
                } else {
                    setText("");
                }
                
            }
        };
    }
});
  }
    
  //iniciar sessió
  @FXML
private void ComprobaLogin(ActionEvent event) throws IOException {
    String username = usernameTextField.getText();
    String password = passwordTextField.getText();
    
    boolean found = false;
    for (Client Client : usuaris) {
        if (Client.getUsername().equals(username) && Client.getPassword().equals(password)) {
            found = true;
            break;
        }
    }
    
    if (found) {

    Parent menuParent = FXMLLoader.load(getClass().getResource("menu.fxml"));
        Scene menuScene = new Scene(menuParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(menuScene);
        window.show();
    } else {
        missatge.setText("usuari o contrasenya incorrectes!");

   
    }
}
//mostrar usuari profile
@FXML
private void mostrarPerfil(){
for (Client usuaris : usuaris) {
      nomUsuari.setText(usuaris.getUsername());
   }

}
//tancar sessió
@FXML
private void tancarSessio(ActionEvent event) throws IOException{
    
    Parent perfilParent = FXMLLoader.load(getClass().getResource("primary.fxml"));
        Scene primaryScene = new Scene(perfilParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(primaryScene);
        window.show(); 
}

}