package ch.beginsecure.ciphertool.system.symEnc.impl;

import ch.beginsecure.ciphertool.system.symEnc.AbstractSymEnc;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class ChaCha20Enc extends AbstractSymEnc {
    // ChaCha20-Poly1305 uses a 12-byte nonce.
    private static final int NONCE_LENGTH = 12;

    @Override
    protected String getAlgorithmName() {
        return "ChaCha20";
    }

    @Override
    protected int getIvLength() {
        return NONCE_LENGTH;
    }

    @Override
    protected Cipher createCipher(int mode, SecretKey key, byte[] nonce) throws Exception {
        IvParameterSpec ivSpec = new IvParameterSpec(nonce);
        Cipher cipher = Cipher.getInstance("ChaCha20-Poly1305");
        cipher.init(mode, key, ivSpec);
        return cipher;
    }
}
