package net.kukinet.kafkatesting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// this is a standalone consumer #1

public class Consumer1 {




    private static final String FAKE_GROUP="fake";
    private static final String RATING_MQ_GROUP="rating-mq-sanity-system-group-system";
    private static final String[] RATING_MQ_ONLY_TOPICS={"rating-mq-sanity-system"};
    private static final String[] RATING_ONLY_TOPICS={
            //"__consumer_offsets",
            "customer-event-LAB_NETANYA",
            "customer-event-MTN_CI",
            "customer-event-MTN_NG",
            "notifications-LAB_NETANYA",
            "notifications-MTN_CI",
            "notifications-MTN_NG",
            "payment-LAB_NETANYA",
            "payment-MTN_CI",
            "payment-MTN_NG",
            "rating-mq-sanity-system",
            "test-topic-1",
            "test-topic-2",
            "test-topic-3"};

    private static final String[] ALL_TOPICS={
            //"__consumer_offsets",

            "activation-LAB_NETANYA",
            "activation-MTN_CI",
            "activation-MTN_NG",
            "activation-flow-job-req-LAB_NETANYA",
            "activation-flow-job-req-MTN_CI",
            "activation-flow-job-req-MTN_NG",
            "activation-notification-LAB_NETANYA",
            "activation-notification-MTN_CI",
            "activation-notification-MTN_NG",
            "backend-mq-sanity-system",
            "charging-events-res-LAB_NETANYA",
            "charging-events-res-MTN_CI",
            "charging-events-res-MTN_NG",
            "crm-updates-LAB_NETANYA",
            "crm-updates-MTN_CI",
            "crm-updates-MTN_NG",
            "customer-event-LAB_NETANYA",
            "customer-event-MTN_CI",
            "customer-event-MTN_NG",
            "fota-job-req-LAB_NETANYA",
            "fota-job-req-MTN_CI",
            "fota-job-req-MTN_NG",
            "job-req-LAB_NETANYA",
            "job-req-MTN_CI",
            "job-req-MTN_NG",
            "notifications-LAB_NETANYA",
            "notifications-MTN_CI",
            "notifications-MTN_NG",
            "payment-LAB_NETANYA",
            "payment-MTN_CI",
            "payment-MTN_NG",
            "rating-mq-sanity-system",
            "sps-cmd-log-LAB_NETANYA",
            "sps-cmd-log-MTN_CI",
            "sps-cmd-log-MTN_NG",
            "sps-cmd-req-LAB_NETANYA",
            "sps-cmd-req-MTN_CI",
            "sps-cmd-req-MTN_NG",
            "sps-cmd-res-LAB_NETANYA",
            "sps-cmd-res-MTN_CI",
            "sps-cmd-res-MTN_NG"};


    Logger logger = LoggerFactory.getLogger(Consumer1.class);
    private static final String TOPIC = "payment-LAB_NETANYA";
//    private static final String GROUP_ID = "con2";
    private static final String GROUP_ID = FAKE_GROUP;
//    private static final String GROUP_ID = RATING_MQ_GROUP;
    private static final int CONSUMER_ID = 1;

    public static void main(String[] args) {
//        List<String> topics = Arrays.asList(TOPIC);
        List<String> topics = Arrays.asList(RATING_MQ_ONLY_TOPICS);
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
