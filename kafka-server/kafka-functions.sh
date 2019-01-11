#!/bin/bash

# function to simplify kafka commands
# source this file into your shell
# ie:
# ~ source ./kafka-functions.sh

function kafka-version {
    docker exec -ti $(docker ps |  grep kafka1_1 | awk '{print $1}') \
       /bin/bash -c "ls /opt/kafka/libs | grep kafka_ | head -n1"
}

function kafka-produce {
    if [[ -z "$1" ]] || [[ -z "$2" ]]; then
        echo "usage: kafka-produce {topic} {count}"
        return
    fi
    docker exec -ti $(docker ps |  grep kafka1_1 | awk '{print $1}') \
       /bin/bash -c "/opt/kafka/bin/kafka-verifiable-producer.sh \
               --topic $1 --max-messages $2 --broker-list localhost:9092"
}

function kafka-list-topics {
docker exec -ti $(docker ps |  grep kafka1_1 | awk '{print $1}') \
   /bin/bash -c "/opt/kafka/bin/kafka-topics.sh --list --zookeeper \$KAFKA_ZOOKEEPER_CONNECT"
}

function kafka-list-consumer-groups {
docker exec -ti $(docker ps |  grep kafka1_1 | awk '{print $1}') \
   /bin/bash -c "/opt/kafka/bin/kafka-consumer-groups.sh --list --zookeeper \$KAFKA_ZOOKEEPER_CONNECT"
}

function kafka-describe-consumer-groups {
    # if cgroup supplied
    if [[ ! -z "$1" ]]; then
        cgroup=$1
        docker exec -ti $(docker ps |  grep kafka1_1 | awk '{print $1}') \
           /bin/bash -c "/opt/kafka/bin/kafka-consumer-groups.sh --describe --group ${cgroup} --zookeeper \$KAFKA_ZOOKEEPER_CONNECT"
   # if none provided, loop all TODO: fix error when invoke with no params
    else
        for cgroup in $(kafka-list-consumer-groups); do
        echo $cgroup
        docker exec -ti $(docker ps |  grep kafka1_1 | awk '{print $1}') \
               /bin/bash -c "/opt/kafka/bin/kafka-consumer-groups.sh --describe --group ${cgroup} --zookeeper \$KAFKA_ZOOKEEPER_CONNECT"
        sleep 3s
        done
    fi
}

function kafka-create-topic {
    if [[ -z "$1" ]] || [[ -z "$2" ]] || [[ -z "$3" ]] ; then
        echo "usage: kafka-create-topic {name} {replication factor} {partitions}"
        return
    fi
    docker exec -ti $(docker ps |  grep kafka1_1 | awk '{print $1}') \
       /bin/bash -c "
           /opt/kafka/bin/kafka-topics.sh --create --zookeeper \$KAFKA_ZOOKEEPER_CONNECT \
               --topic $1 --replication-factor $2 --partitions $3"
}
