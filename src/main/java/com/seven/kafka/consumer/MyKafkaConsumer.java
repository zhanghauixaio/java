package com.seven.kafka.consumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.util.*;
import java.util.regex.Pattern;

public class MyKafkaConsumer {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.31.129:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
        properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000");
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1000");
        properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "30000");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);
        Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<>();
        consumer.subscribe(Pattern.compile("test"), new ConsumerRebalanceListener() {
            @Override
            public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
                consumer.commitSync(currentOffsets);
            }

            @Override
            public void onPartitionsAssigned(Collection<TopicPartition> partitions) {

            }
        });
        ConsumerRecords<String, String> records = consumer.poll(1000L);
        Set<TopicPartition> assignment = consumer.assignment();
        List<TopicPartition> topicPartitions = Arrays.asList(new TopicPartition("test", 0), new TopicPartition("test", 1));
        // 从最新的开始消费
        // consumer.seekToEnd(topicPartitions);
        // 从开始消费
        consumer.seekToBeginning(topicPartitions);
        // records.partitions().forEach(topicPartition -> {
        //     consumer.seek(topicPartition, 0);
        //     List<ConsumerRecord<String, String>> recordList = records.records(topicPartition);
        //     recordList.get(recordList.size() - 1).offset();
        // });

        records.forEach(record -> {
            currentOffsets.put(new TopicPartition(record.topic(), record.partition()), new OffsetAndMetadata(record.offset()));
            System.out.println("offset: " + record.offset() + " key: " +record.key() + " value: " + record.value());
        });
        consumer.commitAsync(currentOffsets, null);
        // consumer.endOffsets(topicPartitions).values().forEach(System.out::println);
        consumer.close();
    }
}
