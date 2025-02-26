package ch.beginsecure.ciphertool.utils;

import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class FileUtils {

    public static File openFileDialog(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select a File");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files", "*.*"),
                new FileChooser.ExtensionFilter("PDF Files", "*.pdf"),
                new FileChooser.ExtensionFilter("Word Documents", "*.docx")
        );

        return fileChooser.showOpenDialog(stage);
    }

    public static File createOutputFile(File inputFile, boolean isEncryption) {
        String parentDir = inputFile.getParent();
        String fileName = inputFile.getName();
        String prefix = isEncryption ? "enc_" : "dec_";

        return new File(parentDir, prefix + fileName);
    }
}
