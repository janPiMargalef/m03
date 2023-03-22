module com.mycompany.caixerautomaticm03 {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.caixerautomaticm03 to javafx.fxml;
    exports com.mycompany.caixerautomaticm03;
}
