/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.caixerautomaticm03;
import java.util.ArrayList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
/**
 *
 * @author alumne
 */
public class Caixer {
//arraylist = new arraylist...
     @FXML
     TextField usernameTextField;
      @FXML
      TextField passwordTextField;
       @FXML
       Label missatge;
    
 public static ArrayList<Client> usuaris = new ArrayList<Client>();
 
  public void initialize() {
        // Populate the users ArrayList with some example users
        usuaris.add(new Client("prova1", "pass1"));
  }
 
  @FXML
private void ComprobaLogin(ActionEvent event) {
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
        missatge.setText("FUNCIONA");
    } else {
        missatge.setText("NO FUNCIONA");
    }
}
    
}
