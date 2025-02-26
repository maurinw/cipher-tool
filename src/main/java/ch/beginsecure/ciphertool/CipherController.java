package ch.beginsecure.ciphertool;

import ch.beginsecure.ciphertool.system.SystemManager;
import ch.beginsecure.ciphertool.utils.FileUtils;
import ch.beginsecure.ciphertool.utils.UIUtils;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.File;

public class CipherController {

    private final SystemManager systemManager = new SystemManager();

    @FXML
    private TextField filePathField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private ComboBox<String> algorithmComboBox;

    @FXML
    public void initialize() {
        algorithmComboBox.getItems().addAll(systemManager.getAlgorithmNames());
        algorithmComboBox.getSelectionModel().selectFirst(); // Set default selection
    }

    @FXML
    protected void handleBrowseButtonClick() {
        Stage stage = (Stage) filePathField.getScene().getWindow();
        File selectedFile = FileUtils.openFileDialog(stage);
        if (selectedFile != null) {
            filePathField.setText(selectedFile.getAbsolutePath());
        }
    }

    @FXML
    protected void handleEncryptButtonClick() {
        processFile(true);
    }

    @FXML
    protected void handleDecryptButtonClick() {
        processFile(false);
    }

    private void processFile(boolean isEncryption) {
        String filePath = filePathField.getText();
        String password = passwordField.getText();
        String selectedAlgorithm = algorithmComboBox.getValue();

        if (filePath.isEmpty()) {
            UIUtils.showError("Please select a file.");
            return;
        }
        if (password.isEmpty()) {
            UIUtils.showError("Please enter a password.");
            return;
        }
        if (selectedAlgorithm == null) {
            UIUtils.showError("Please select an encryption algorithm.");
            return;
        }

        File inputFile = new File(filePath);
        if (!inputFile.exists() || !inputFile.isFile()) {
            UIUtils.showError("The selected file does not exist or is invalid.");
            return;
        }

        File outputFile = FileUtils.createOutputFile(inputFile, isEncryption);
        if (outputFile == null) {
            UIUtils.showError("Failed to create output file.");
            return;
        }

        try {
            if (isEncryption) {
                systemManager.encrypt(selectedAlgorithm, inputFile, outputFile, password);
                UIUtils.showInfo("File successfully encrypted: " + outputFile.getAbsolutePath());
            } else {
                systemManager.decrypt(selectedAlgorithm, inputFile, outputFile, password);
                UIUtils.showInfo("File successfully decrypted: " + outputFile.getAbsolutePath());
            }
        } catch (Exception e) {
            UIUtils.showError("Operation failed: " + e.getMessage());
        }
    }
}
