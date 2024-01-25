package com.seven.kafka.producer;

import org.apache.kafka.clients.admin.CreateTopicsResult;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Arrays;
import java.util.Properties;

public class MyKafkaProducer {
    public static void main(String[] args) {
        // new KafkaClient();
        Properties properties = new Properties();
        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.31.129:9092");
        properties.setProperty(ProducerConfig.ACKS_CONFIG, "1");
        properties.setProperty(ProducerConfig.RETRIES_CONFIG, "3");
        properties.put(ProducerConfig.BATCH_SIZE_CONFIG, 2);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<String, String>(properties);
        // NewTopic topic = new NewTopic("test", 2, (short) 1);
        // CreateTopicsResult topicsResult = client.createTopics(Arrays.asList(topic));
        // client.describeTopics(Arrays.asList("test"));
        String data = "jagaag";
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>("test", data);
        for (int i = 0; i < 10; i++) {
            producer.send(producerRecord, (metadata, exception) -> {
                System.out.println("分区：" + metadata.partition() + "; 发送的偏移：" + metadata.offset() + "; 主题：" + metadata.topic());
            });
        }
        producer.close();
    }
}
