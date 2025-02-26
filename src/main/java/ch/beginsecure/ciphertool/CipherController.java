package ch.beginsecure.ciphertool;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class CipherController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}