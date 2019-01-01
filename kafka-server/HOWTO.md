
This file contains some example commands that can be used from cli. it executes kafka commands
from the container itself for simplicity and oneliner execution

* check running kafka version:
```
KAFKA_VERSION=$(docker exec -ti $(docker ps |  grep kafka1_1 | awk '{print $1}') \
   /bin/bash -c "ls /opt/kafka/libs | grep kafka_ | head -n1"); echo $KAFKA_VERSION
```

* create a topic

```
docker exec -ti $(docker ps |  grep kafka1_1 | awk '{print $1}') \
   /bin/bash -c "
       /opt/kafka/bin/kafka-topics.sh \
           --create \
           --zookeeper \$KAFKA_ZOOKEEPER_CONNECT \
           --replication-factor 1 --partitions 1 --topic topic-rf1-p1"
```

* create topic 3 partitions, specifying only 1 zookeeper

```
docker exec -ti $(docker ps |  grep kafka1_1 | awk '{print $1}') \
   /bin/bash -c "
       /opt/kafka/bin/kafka-topics.sh \
           --create \
           --zookeeper 192.168.2.57:2181 \
           --replication-factor 3 --partitions 3 --topic topic-p3"
```

* producing test messages 

```
sleep 5s ; docker exec -ti $(docker ps |  grep kafka1_1 | awk '{print $1}') \
   /bin/bash -c "
       /opt/kafka/bin/kafka-verifiable-producer.sh \
           --topic topic-sdp-1p --max-messages 10 --broker-list localhost:9092"
```

* list topics

```
docker exec -ti $(docker ps |  grep kafka1_1 | awk '{print $1}') \
   /bin/bash -c "
       /opt/kafka/bin/kafka-topics.sh --list --zookeeper 192.168.2.57:2181"
```

* list consumer groups

```
docker exec -ti $(docker ps |  grep kafka1_1 | awk '{print $1}') \
   /bin/bash -c "
       /opt/kafka/bin/kafka-consumer-groups.sh \
           --list --bootstrap-server localhost:9092"
```

* describe consumer groups (offsets are pre cgroup !)

```
docker exec -ti $(docker ps |  grep kafka1_1 | awk '{print $1}') \
   /bin/bash -c "
       /opt/kafka/bin/kafka-consumer-groups.sh \
           --describe --group sdp-group --bootstrap-server localhost:9092"
```

* create topic sdp topic-sdp-2p

```
docker exec -ti $(docker ps |  grep kafka1_1 | awk '{print $1}') \
   /bin/bash -c "
       /opt/kafka/bin/kafka-topics.sh \
           --create \
           --zookeeper 192.168.2.57:2181,192.168.2.57:2182,192.168.2.57:2183 \
           --replication-factor 3 --partitions 2 --topic topic-sdp-2p"
```

* create topic topic3

```
docker exec -ti $(docker ps |  grep kafka1_1 | awk '{print $1}') \
   /bin/bash -c "
       /opt/kafka/bin/kafka-topics.sh \
           --create \
           --zookeeper 192.168.2.57:2181,192.168.2.57:2182,192.168.2.57:2183 \
           --replication-factor 3 --partitions 3 --topic topic3"
```