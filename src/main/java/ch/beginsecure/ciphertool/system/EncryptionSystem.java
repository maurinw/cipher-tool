package ch.beginsecure.ciphertool.system;

import java.io.File;

public interface EncryptionSystem {
    void encrypt(File inputFile, File outputFile, String password) throws Exception;
    void decrypt(File inputFile, File outputFile, String password) throws Exception;
}
