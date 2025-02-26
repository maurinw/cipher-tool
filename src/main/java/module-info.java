module ch.beginsecure.ciphertool {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires java.desktop;

    opens ch.beginsecure.ciphertool to javafx.fxml;
    exports ch.beginsecure.ciphertool;
}