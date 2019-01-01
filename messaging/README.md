# Kafka Consumers / Producers

This module introduce consumers/producers 
standalone java applications for testing kafka behavior, 
It assumes that you have kafka up and running. 

* see the `kafka-server` module for understanding how to run the
kafka cluster, it forked from [zoidbergwill/docker-compose-kafka](https://github.com/zoidbergwill/docker-compose-kafka). 
under The MIT License (MIT) Copyright (c) 2016 William Martin Stewart.
## Overview

The kafkatesting package has 3 classes with main method:

* Consumer1
* Consumer2
* Producer1

The consumers code is duplicated because i want to have 2 independent application, 
each can be configured/start/stop separately. Using multithreading can be complex
and will not achieve the goal of this module.

## Consuming patterns
Kafka manages offsets :
* per topic
* per partition
* per consumer-group

This allows the following consuming patterns:

#### competing consumers:
* if both consumers are in the same group, they will compete and only 1 will get a copy of a message.


#### pub-sub
* if both consumers are in a different group. kafka manage the offset seperately, thus both will have a copy of the same message 

## Configure parameters

set the values of the consumers/producers as u wish:

    private static final String TOPIC = "topic3";
    private static final String GROUP_ID = "g5";
    private static final int CONSUMER_ID = 1;

* `CONSUMER_ID` is only for nice log output
* `GROUP_ID` will define the behaviour of the consumers


## Testing workflow

1. Run the application Consumer1 (and/or Consumer2)
1. Run the application Producer1
1. see console log
1. use terminal window to use kafka-cli commands, see examples in 
`../kafka-server/HOWTO.md`
