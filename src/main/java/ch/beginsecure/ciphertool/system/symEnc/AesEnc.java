package ch.beginsecure.ciphertool.system.symEnc;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class AesEnc extends AbstractSymEnc {
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
