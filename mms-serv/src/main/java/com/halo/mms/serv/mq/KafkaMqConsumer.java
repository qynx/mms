package com.halo.mms.serv.mq;

import com.halo.mms.common.config.KafkaConfig;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

public class KafkaMqConsumer {

    private KafkaConfig kafkaConfig;
    private KafkaConsumer<String, String> consumer;
    private Executor executor = Executors.newSingleThreadExecutor();

    public KafkaMqConsumer(KafkaConfig kafkaConfig) {
        Properties properties = new Properties();
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaConfig.getGroup());
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, Optional.ofNullable(kafkaConfig.getServers())
            .orElse("127.0.0.1:9092"));
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        this.consumer = new KafkaConsumer<String, String>(properties);
        this.consumer.subscribe(Collections.singletonList(kafkaConfig.getTopic()));
        this.kafkaConfig = kafkaConfig;
    }

    public void start(Consumer<ConsumerRecords<String, String>> consumer) {
        KafkaMqConsumer _this = this;
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                while (true) {
                    ConsumerRecords<String, String> records = _this.consumer.poll(Duration.ofSeconds(1L));
                    consumer.accept(records);
                    _this.consumer.commitAsync();
                }
            }
        };
        executor.execute(runnable);
    }

    public void close() {
        this.consumer.close();
    }
}
