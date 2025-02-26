package ch.beginsecure.ciphertool.system.symEnc;

import ch.beginsecure.ciphertool.system.EncryptionSystem;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.SecureRandom;
import java.security.spec.KeySpec;

public abstract class AbstractSymEnc implements EncryptionSystem {
    protected static final int KEY_SIZE = 256;
    protected static final int ITERATIONS = 65536;
    protected static final int SALT_LENGTH = 16;

    protected abstract int getIvLength();
    protected abstract Cipher createCipher(int mode, SecretKey key, byte[] ivOrNonce) throws Exception;
    protected abstract String getAlgorithmName();

    protected SecretKey deriveKey(String password, byte[] salt) throws Exception {
        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        KeySpec spec = new PBEKeySpec(password.toCharArray(), salt, ITERATIONS, KEY_SIZE);
        SecretKey tmp = factory.generateSecret(spec);
        return new SecretKeySpec(tmp.getEncoded(), getAlgorithmName());
    }

    @Override
    public void encrypt(File inputFile, File outputFile, String password) throws Exception {
        // Generate a random salt.
        byte[] salt = new byte[SALT_LENGTH];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(salt);

        SecretKey key = deriveKey(password, salt);

        // Generate a random IV or nonce (length depends on algorithm).
        int ivLength = getIvLength();
        byte[] iv = new byte[ivLength];
        sr.nextBytes(iv);

        Cipher cipher = createCipher(Cipher.ENCRYPT_MODE, key, iv);

        try (FileOutputStream fos = new FileOutputStream(outputFile)) {
            // Write header: first salt, then IV/nonce.
            fos.write(salt);
            fos.write(iv);
            try (CipherOutputStream cos = new CipherOutputStream(fos, cipher);
                 FileInputStream fis = new FileInputStream(inputFile)) {
                byte[] buffer = new byte[4096];
                int read;
                while ((read = fis.read(buffer)) != -1) {
                    cos.write(buffer, 0, read);
                }
            }
        }
    }

    @Override
    public void decrypt(File inputFile, File outputFile, String password) throws Exception {
        try (FileInputStream fis = new FileInputStream(inputFile)) {
            byte[] salt = new byte[SALT_LENGTH];
            if (fis.read(salt) != SALT_LENGTH)
                throw new IOException("Invalid salt length");

            int ivLength = getIvLength();
            byte[] iv = new byte[ivLength];
            if (fis.read(iv) != ivLength)
                throw new IOException("Invalid IV/Nonce length");

            SecretKey key = deriveKey(password, salt);
            Cipher cipher = createCipher(Cipher.DECRYPT_MODE, key, iv);

            try (CipherInputStream cis = new CipherInputStream(fis, cipher);
                 FileOutputStream fos = new FileOutputStream(outputFile)) {
                byte[] buffer = new byte[4096];
                int read;
                while ((read = cis.read(buffer)) != -1) {
                    fos.write(buffer, 0, read);
                }
            }
        }
    }
}
