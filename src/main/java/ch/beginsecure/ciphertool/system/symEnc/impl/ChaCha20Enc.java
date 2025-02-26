package ch.beginsecure.ciphertool.system.symEnc.impl;

import ch.beginsecure.ciphertool.system.symEnc.AbstractSymEnc;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class ChaCha20Enc extends AbstractSymEnc {
    @Override
    protected int getIvLength() {
        return 0;
    }

    @Override
    protected Cipher createCipher(int mode, SecretKey key, byte[] ivOrNonce) throws Exception {
        return null;
    }

    @Override
    protected String getAlgorithmName() {
        return "";
    }
}
