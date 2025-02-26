package ch.beginsecure.ciphertool.system.symEnc.impl;

import ch.beginsecure.ciphertool.system.symEnc.AbstractSymEnc;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class AesEnc extends AbstractSymEnc {
    // AES with CBC/PKCS5Padding requires a 16-byte IV.
    private static final int IV_LENGTH = 16;

    @Override
    protected String getAlgorithmName() {
        return "AES";
    }

    @Override
    protected int getIvLength() {
        return IV_LENGTH;
    }

    @Override
    protected Cipher createCipher(int mode, SecretKey key, byte[] iv) throws Exception {
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        cipher.init(mode, key, ivSpec);
        return cipher;
    }
}
