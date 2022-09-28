package io.streamnative.demo;

import org.apache.pulsar.client.api.*;

public class ProducerThread extends CryptoThread {

    private Producer<String> producer;

    private long counter = 0L;

    public ProducerThread(PulsarClient pulsarClient, CryptoKeyReader cryptoKeyReader, String topicName) {
        super(pulsarClient, cryptoKeyReader, topicName);
    }

    public void run() {
        do {

            try {
                String msg = "Message #" + counter++;
                System.out.println("Sending message " + msg);
                getProducer().send(msg);
                Thread.sleep(100);
            } catch (PulsarClientException | InterruptedException e) {
                e.printStackTrace();
            }

        } while (true);
    }

    private Producer<String> getProducer() throws PulsarClientException {
        if (producer == null) {
            producer = pulsarClient.newProducer(Schema.STRING)
                    .topic(topicName)
                    .addEncryptionKey("producer-key")
                    .cryptoFailureAction(ProducerCryptoFailureAction.FAIL)
                    .cryptoKeyReader(cryptoKeyReader)
                    .create();
        }
        return producer;
    }
}
