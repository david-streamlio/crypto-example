package io.streamnative.demo;

import org.apache.pulsar.client.api.*;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CryptoDemo {

    private static ExecutorService executor = Executors.newFixedThreadPool(5);

    private static final String PULSAR_URL = "pulsar://192.168.1.121:6650";
    private static final String TOPIC = "persistent://public/default/crypto-demo";

    public static void main(String[] args) throws Exception {

       PulsarClient client = PulsarClient.builder()
               .serviceUrl(PULSAR_URL)
               .build();

       CryptoKeyReader cryptoKeyReader = new RawFileKeyReader(
               "/Users/david/clone-zone/personal/crytpo-example/src/test/test_ecdsa_pubkey.pem",
               "/Users/david/clone-zone/personal/crytpo-example/src/test/test_ecdsa_privkey.pem");

       ProducerThread producerThread = new ProducerThread(client, cryptoKeyReader, TOPIC);
       ConsumerThread consumerThread = new ConsumerThread(client, cryptoKeyReader, TOPIC);

       executor.execute(producerThread);
       executor.execute(consumerThread);
    }
}
