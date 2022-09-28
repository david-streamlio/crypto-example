package io.streamnative.demo;

import org.apache.pulsar.client.api.CryptoKeyReader;
import org.apache.pulsar.client.api.EncryptionKeyInfo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

public class RawFileKeyReader implements CryptoKeyReader {

    private final String publicKeyFile;
    private final String privateKeyFile;

    public RawFileKeyReader(String publicKeyFile, String privateKeyFile) {
        this.publicKeyFile = publicKeyFile;
        this.privateKeyFile = privateKeyFile;
    }

    @Override
    public EncryptionKeyInfo getPublicKey(String keyName, Map<String, String> metadata) {
        EncryptionKeyInfo keyInfo = new EncryptionKeyInfo();
        try {
            // Read the public key from the file
            keyInfo.setKey(Files.readAllBytes(Paths.get(publicKeyFile)));
        } catch (IOException e) {
            System.err.println("Failed to read public key from file " + publicKeyFile);
            e.printStackTrace(System.err);
        }
        return keyInfo;
    }

    @Override
    public EncryptionKeyInfo getPrivateKey(String keyName, Map<String, String> metadata) {
        EncryptionKeyInfo keyInfo = new EncryptionKeyInfo();
        try {
            // Read the private key from the file
            keyInfo.setKey(Files.readAllBytes(Paths.get(privateKeyFile)));
        } catch (IOException e) {
            System.err.println("Failed to read private key from file " + privateKeyFile);
            e.printStackTrace(System.err);
        }
        return keyInfo;
    }
}
