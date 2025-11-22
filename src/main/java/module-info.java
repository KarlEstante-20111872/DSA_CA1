module dsa_ca1 {
    requires javafx.controls;
    requires javafx.fxml;
    requires xstream;

    opens dsa_ca1.controllers to javafx.fxml;
    opens dsa_ca1.main to javafx.fxml;

    exports dsa_ca1.controllers;
    exports dsa_ca1.main;
}
