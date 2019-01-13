package net.kukinet.kafkatesting;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

public class Producer1 {

    private static Logger logger = LoggerFactory.getLogger(Producer1.class);

//    private static final String TOPIC = "topic3p";
    private static final String TOPIC = "single";
    private static int NUM_OF_MESSAGES;
    private static int DELAY_MSEC;
    private static int WAIT_SECONDS;

    public static void main(String args[]) throws Exception{
        // identify current execution of this program
        long executionId = geUniqueId();
        logger.warn("producer {} started.", executionId);

        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092,localhost:9093,localhost:9094");
        props.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //
        // program accepts batches. each batch described as m:d:w :
        // messages:delay-msec-between-messages:wait-seconds-before-continue
        // ie:
        // ./app.java m:d:w m:d:w m:d:w m:d:w
        // ./app.java 6:100:1 6:200:2 6:100:1 6:200:2
        //
        int batch_id = 0;
        for (String arg : args){
            // foreach batch
            NUM_OF_MESSAGES = Integer.parseInt(arg.split(":")[0]);
            DELAY_MSEC      = Integer.parseInt(arg.split(":")[1]);
            WAIT_SECONDS    = Integer.parseInt(arg.split(":")[2]);

            logger.warn("batch {}: start [execution:{}, messages:{}, delay_msec:{}, wait:{}].", batch_id,
                    executionId, NUM_OF_MESSAGES, DELAY_MSEC, WAIT_SECONDS);
//
//            logger.warn("pruducing messages for [execution:{}, batch:{}, messages:{}, delay_msec:{}, wait:{}].", executionId,
//                    , NUM_OF_MESSAGES, DELAY_MSEC, WAIT_SECONDS);

            Producer<Integer, String> producer = new KafkaProducer<Integer, String>(props);

            for (int i = 0; i < NUM_OF_MESSAGES; i++) {
                ProducerRecord producerRecord =
                        new ProducerRecord<Integer, String>(TOPIC, i, executionId + "-" + batch_id + "-" + i);
                producer.send(producerRecord);
                Thread.sleep(DELAY_MSEC);
            }
            logger.warn("batch {}: closing producer.", batch_id);
            producer.close();
            logger.warn("batch {}: start waiting {} seconds.", batch_id, WAIT_SECONDS);
            Thread.sleep(WAIT_SECONDS * 1000);
            logger.warn("batch {}: done.", batch_id);

            batch_id++;
        }
        logger.warn("producer {}: job done.", executionId);

    }
    private static long geUniqueId() {
        return System.currentTimeMillis() / 1000;
    }
}
