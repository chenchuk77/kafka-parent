package net.kukinet.kafkatesting;

import kafka.consumer.ConsumerConfig;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class OldConsumer1 {

    private static Logger logger = LoggerFactory.getLogger(OldConsumer1.class);

    // replacing program args with constants
    private static final String ZOOKEEPER = "localhost:2181";
    private static final String GROUP_ID = "oldgroup";
//    private static final String TOPIC = "rating-mq-sanity-system";
    private static final String TOPIC = "topic3p";
    private static final int NUM_THREADS =3;

    private final ConsumerConnector consumer;
    private final String topic;
    private  ExecutorService executor;

    public OldConsumer1(String a_zookeeper, String a_groupId, String a_topic) {
        consumer = kafka.consumer.Consumer.createJavaConsumerConnector(
                createConsumerConfig(a_zookeeper, a_groupId));
        this.topic = a_topic;
    }

    public void shutdown() {
        if (consumer != null) consumer.shutdown();
        if (executor != null) executor.shutdown();
        try {
            if (!executor.awaitTermination(5000, TimeUnit.MILLISECONDS)) {
                System.out.println("Timed out waiting for consumer threads to shut down, exiting uncleanly");
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted during shutdown, exiting uncleanly");
        }
    }

    public void run(int a_numThreads) {
        Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
        topicCountMap.put(topic, new Integer(a_numThreads));
        Map<String, List<KafkaStream<byte[], byte[]>>> consumerMap = consumer.createMessageStreams(topicCountMap);
        List<KafkaStream<byte[], byte[]>> streams = consumerMap.get(topic);

        // now launch all the threads
        //
        executor = Executors.newFixedThreadPool(a_numThreads);

        // now create an object to consume the messages
        //
        int threadNumber = 0;
        for (final KafkaStream stream : streams) {
            executor.submit(new OldConsumerTest(stream, threadNumber));
            threadNumber++;
        }
    }

    private static ConsumerConfig createConsumerConfig(String a_zookeeper, String a_groupId) {
        Properties props = new Properties();
        props.put("zookeeper.connect", a_zookeeper);
        props.put("group.id", a_groupId);
        props.put("zookeeper.session.timeout.ms", "400");
        props.put("zookeeper.sync.time.ms", "200");
        props.put("auto.commit.interval.ms", "1000");

        return new ConsumerConfig(props);
    }

    public static void main(String[] args) {
        String zooKeeper = ZOOKEEPER; //args[0];
        String groupId = GROUP_ID; //args[1];
        String topic = TOPIC; //args[2];
        int threads = NUM_THREADS; //Integer.parseInt(args[3]);

        OldConsumer1 oldConsumer1 = new OldConsumer1(zooKeeper, groupId, topic);
        oldConsumer1.run(threads);

        try {
            Thread.sleep(10000);
        } catch (InterruptedException ie) {

        }
        oldConsumer1.shutdown();
    }
}