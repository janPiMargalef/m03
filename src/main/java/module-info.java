module com.mycompany.caixer {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.caixer to javafx.fxml;
    exports com.mycompany.caixer;
}
