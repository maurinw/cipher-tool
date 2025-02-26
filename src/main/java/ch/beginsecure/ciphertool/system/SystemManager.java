package ch.beginsecure.ciphertool.system;


import ch.beginsecure.ciphertool.system.symEnc.impl.AesEnc;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SystemManager {
    private Map<String, EncryptionSystem> algorithms = new HashMap<>();

    public SystemManager() {
        registerAlgorithm("AES", new AesEnc());
    }

    public void registerAlgorithm(String name, EncryptionSystem algorithm) {
        algorithms.put(name.toUpperCase(), algorithm);
    }

    public EncryptionSystem getAlgorithm(String name) {
        return algorithms.get(name.toUpperCase());
    }

    public Set<String> getAlgorithmNames() {
        return algorithms.keySet();
    }

    public void encrypt(String algorithmName, File inputFile, File outputFile, String password) throws Exception {
        EncryptionSystem algorithm = getAlgorithm(algorithmName);
        if (algorithm == null) {
            throw new IllegalArgumentException("Algorithmus nicht registriert: " + algorithmName);
        }
        algorithm.encrypt(inputFile, outputFile, password);
    }

    public void decrypt(String algorithmName, File inputFile, File outputFile, String password) throws Exception {
        EncryptionSystem algorithm = getAlgorithm(algorithmName);
        if (algorithm == null) {
            throw new IllegalArgumentException("Algorithmus nicht registriert: " + algorithmName);
        }
        algorithm.decrypt(inputFile, outputFile, password);
    }
}
