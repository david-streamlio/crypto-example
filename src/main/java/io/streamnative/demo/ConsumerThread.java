package io.streamnative.demo;

import org.apache.pulsar.client.api.*;

import java.util.Map;

public class ConsumerThread extends CryptoThread {

    private Consumer<String> consumer;

    protected ConsumerThread(PulsarClient pulsarClient, CryptoKeyReader cryptoKeyReader, String topicName) {
        super(pulsarClient, cryptoKeyReader, topicName);
    }

    @Override
    public void run() {
        do {
            try {
                Message<String> msg = getConsumer().receive();
                System.out.println("Received message " + msg.getValue());
                getConsumer().acknowledge(msg);
            } catch (PulsarClientException e) {
                throw new RuntimeException(e);
            }
        } while (true);
    }

    private final Consumer<String> getConsumer() throws PulsarClientException {

        if (consumer == null) {
            consumer = pulsarClient.newConsumer(Schema.STRING)
                    .topic(topicName)
                    .cryptoFailureAction(ConsumerCryptoFailureAction.FAIL)
                    .cryptoKeyReader(cryptoKeyReader)
                    .subscriptionName("encryption-subscription")
                    .subscriptionInitialPosition(SubscriptionInitialPosition.Earliest)
                    .subscribe();
        }

        return consumer;
    }
}
