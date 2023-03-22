package com.mycompany.caixerautomaticm03;

import java.io.IOException;
import javafx.fxml.FXML;

public class PrimaryController {

     @FXML
     private void ferLogin(){
         //App.caixer.login("username", "password");
     }
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }
}
