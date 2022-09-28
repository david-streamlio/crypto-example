package io.streamnative.demo;

import org.apache.pulsar.client.api.CryptoKeyReader;
import org.apache.pulsar.client.api.PulsarClient;

public abstract class CryptoThread extends Thread {

    protected final PulsarClient pulsarClient;

    protected final CryptoKeyReader cryptoKeyReader;

    protected final String topicName;


    protected CryptoThread(PulsarClient pulsarClient, CryptoKeyReader cryptoKeyReader, String topicName) {
        this.pulsarClient = pulsarClient;
        this.cryptoKeyReader = cryptoKeyReader;
        this.topicName = topicName;
    }

    public abstract void run();
}
