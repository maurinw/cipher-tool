package ch.beginsecure.ciphertool.system.symEnc;

import ch.beginsecure.ciphertool.system.EncryptionSystem;

import java.io.File;

public class AbstractSymEnc implements EncryptionSystem {
    @Override
    public void encrypt(File inputFile, File outputFile, String password) throws Exception {

    }

    @Override
    public void decrypt(File inputFile, File outputFile, String password) throws Exception {

    }
}
