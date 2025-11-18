module org.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires xstream;


    opens org.example.demo to javafx.fxml;
    opens dsa_ca1 to javafx.fxml;
    exports dsa_ca1.controllers;
    opens dsa_ca1.controllers to javafx.fxml;
    exports dsa_ca1.main;
    opens dsa_ca1.main to javafx.fxml;
}