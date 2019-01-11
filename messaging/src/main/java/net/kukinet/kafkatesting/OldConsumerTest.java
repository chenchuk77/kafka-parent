package net.kukinet.kafkatesting;

/**
 * Created by lms on 10/01/19.
 */
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OldConsumerTest implements Runnable {
    private static Logger logger = LoggerFactory.getLogger(OldConsumerTest.class);
    private KafkaStream m_stream;
    private int m_threadNumber;

    public OldConsumerTest(KafkaStream a_stream, int a_threadNumber) {
        m_threadNumber = a_threadNumber;
        m_stream = a_stream;
    }

    public void run() {
        ConsumerIterator<byte[], byte[]> it = m_stream.iterator();
        int numOfMessages = 0;
        while (it.hasNext()){
            logger.warn(indented(new String(it.next().message()), m_threadNumber));
            numOfMessages++;
      }
        logger.warn(indented("done: " + numOfMessages, m_threadNumber));
    }

    // indenting by thread-id for pretty output
    private String indented(String s, int tabs){
        while (tabs > 0){
            s = "\t\t\t\t" +s;
            tabs--;
        }
        return s;
    }
}