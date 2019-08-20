package net.kukinet.kafkatesting;

import kafka.utils.ZKStringSerializer$;
import kafka.utils.ZkUtils;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.ZkConnection;
import scala.collection.JavaConversions;

import java.util.List;

/**
 * Created by lms on 16/01/19.
 */
public class ZooKeeper {

    public ZooKeeper(){
        final ZkConnection zkConnection = new ZkConnection("localhost:2181");
        final int sessionTimeoutMs = 10 * 1000;
        final int connectionTimeoutMs = 20 * 1000;
        final ZkClient zkClient = new ZkClient("localhost:2181",
                sessionTimeoutMs,
                connectionTimeoutMs,
                ZKStringSerializer$.MODULE$);

        final ZkUtils zkUtils = new ZkUtils(zkClient, zkConnection, false);

        System.out.println(scala.collection.JavaConversions.seqAsJavaList(zkUtils.getAllBrokersInCluster()));
//        scala.collection.JavaConversions.seqAsJavaList(zkUtils.getAllBrokersInCluster());


//        ZkClient zkClient = new ZkClient("zkHost:zkPort");
//        List<String> topics = JavaConversions.asJavaList(ZkUtils.getAllTopics(zkClient));


    }
    public static void main(String[] args) {
        new ZooKeeper();
    }
}
