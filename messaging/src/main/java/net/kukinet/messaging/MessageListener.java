package net.kukinet.messaging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;

import java.util.concurrent.CountDownLatch;

public class MessageListener {

    private Logger logger = LoggerFactory.getLogger(MessageListener.class);
    private CountDownLatch latch = new CountDownLatch(3);

    @KafkaListener(topics = "${message.topic.name}", containerFactory = "fooKafkaListenerContainerFactory")
    public void listenGroupFoo(String message) {
        logger.warn("Received Messasge in group 'foo': " + message);
        latch.countDown();
    }

//    @KafkaListener(topics = "${message.topic.name}", group = "bar", containerFactory = "barKafkaListenerContainerFactory")
//    public void listenGroupBar(String message) {
//        logger.warn("Received Messasge in group 'bar': " + message);
//        latch.countDown();
//    }
}

