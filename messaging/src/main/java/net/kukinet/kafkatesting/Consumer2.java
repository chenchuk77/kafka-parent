package net.kukinet.kafkatesting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// this is a standalone consumer #2

public class Consumer2 {

    Logger logger = LoggerFactory.getLogger(Consumer2.class);
    private static final String TOPIC = "topic3";
    private static final String GROUP_ID = "g6";
    private static final int CONSUMER_ID = 2;

    public static void main(String[] args) {
        List<String> topics = Arrays.asList(TOPIC);
        final ExecutorService executor = Executors.newFixedThreadPool(1);

        final ConsumerLoop consumer = new ConsumerLoop(CONSUMER_ID, GROUP_ID, topics);
        executor.submit(consumer);

        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                consumer.shutdown();
                executor.shutdown();
                try {
                    executor.awaitTermination(5000, TimeUnit.MILLISECONDS);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
