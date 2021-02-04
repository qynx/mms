package com.halo.mms.serv.mq;

import com.halo.mms.common.config.KafkaConfig;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.Future;

public class KafkaMqProducer {

    private static final String DEFAULT_SERVERS = "127.0.0.1:9092";

    private KafkaConfig config;
    private KafkaProducer<String, String> producer;

    public KafkaMqProducer(KafkaConfig config) {
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,
            Optional.ofNullable(config.getServers()).orElse(DEFAULT_SERVERS));
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        this.config = config;
        this.producer = new KafkaProducer<String, String>(properties);
    }

    public void sendMsg(String msg) {
        ProducerRecord<String, String> record = new ProducerRecord<>(config.getTopic(), msg);
        Future<RecordMetadata> future = this.producer.send(record);
    }

    public void close() {
        this.producer.close();
    }
}
