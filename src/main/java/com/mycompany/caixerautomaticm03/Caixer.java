/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caixerautomaticm03;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
/**
 *
 * @author alumne
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
       TextField titularCompteTextField;
       @FXML
       TextField saldoTextField;
       @FXML
       TextField tipoCompteTextField;
       
//crear compte
       @FXML
    private ListView<Compte> comptesListView;

    private ObservableList<Compte> comptesBancaris;

    
       //crear usuari
 public static ArrayList<Client> usuaris = new ArrayList<Client>();
 
  public void initialize() {
<<<<<<< HEAD
        
=======
      
>>>>>>> 9bb29558c5c6af983f852c9b6891fd840c99a533
        usuaris.add(new Client("prova1", "pass1"));
        
        //compte(aixo dona error)
        
        
  }
  @FXML //IMPORTANT proper dia cambiar la classe , implementar if -->if(compteCorrent.isSelected() per definir si es corrent o ahorros, else{...,caambiar el String tipoCompte) + organitzar el sceneBuilder
    private void afegirCompte() {//no funciona, cambiar class afegirCompte
        comptesBancaris = FXCollections.observableArrayList();
        comptesListView.setItems(comptesBancaris);
        String numeroCompte = numeroCompteTextField.getText();
        String titularCompte = titularCompteTextField.getText();
        String tipoCompte = tipoCompteTextField.getText();
        double saldo = Double.parseDouble(saldoTextField.getText());
        Compte nouCompte = new Compte(numeroCompte, titularCompte,tipoCompte, saldo);
        comptesBancaris.add(nouCompte);
        comptesListView.refresh();
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
<<<<<<< HEAD
    Parent menuParent = FXMLLoader.load(getClass().getResource("menu.fxml"));
        Scene menuScene = new Scene(menuParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(menuScene);
        window.show();
    } else {
        missatge.setText("usuari o contrasenya incorrectes!");
=======
        missatge.setText("FUNCIONA"); //dirigir to menu
    } else {
        missatge.setText("NO FUNCIONA"); //contar per bloquejar (boolean bloquejat = false;)
>>>>>>> 9bb29558c5c6af983f852c9b6891fd840c99a533
    }
}

//entrar al perfil
@FXML
private void perfil(ActionEvent event) throws IOException{
    
    Parent perfilParent = FXMLLoader.load(getClass().getResource("perfil.fxml"));
        Scene menuScene = new Scene(perfilParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(menuScene);
        window.show(); 
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
@FXML
private void tornaMenu(ActionEvent event) throws IOException{
    
    Parent menuParent = FXMLLoader.load(getClass().getResource("menu.fxml"));
        Scene menuScene = new Scene(menuParent);
        Stage window = (Stage)((Node)event.getSource()).getScene().getWindow();
        window.setScene(menuScene);
        window.show();
}

}




