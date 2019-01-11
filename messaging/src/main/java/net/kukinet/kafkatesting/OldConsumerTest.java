package net.kukinet.kafkatesting;

/**
 * Created by lms on 10/01/19.
 */
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;

import java.util.concurrent.ThreadPoolExecutor;

public class OldConsumerTest implements Runnable {
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
          System.out.println("Thread " + m_threadNumber + ": " + new String(it.next().message()));
          numOfMessages++;
      }
      System.out.println("No more messasges, total="+numOfMessages + ".");
      System.out.println("Shutting down Thread: " + m_threadNumber);
    }
}