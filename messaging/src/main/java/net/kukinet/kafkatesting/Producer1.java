package net.kukinet.kafkatesting;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class Producer1 {

    private static Logger logger = LoggerFactory.getLogger(Producer1.class);

    private static final String TOPIC = "topic3";
    private static final int NUM_OF_MESSAGES = 30;
    private static final int DELAY_SECONDS = 3;

    public static void main(String args[]) throws Exception{

        logger.warn("producer1 will generate {} messages to topic {} in {} seconds...", NUM_OF_MESSAGES, TOPIC, DELAY_SECONDS);

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        Thread.sleep(DELAY_SECONDS * 1000);

        Producer<Integer, String> producer = new KafkaProducer<Integer, String>(props);
        for(int i = 0; i < NUM_OF_MESSAGES; i++) {
            ProducerRecord producerRecord =
                    new ProducerRecord<Integer, String>(TOPIC, i, "produced msg # " + i);
            producer.send(producerRecord);
        }

        producer.close();
        logger.warn("producer1 job done.");

    }

}