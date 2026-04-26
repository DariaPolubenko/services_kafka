package ru.daria.kafka.test.containers;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.kafka.KafkaContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.Collections;
import java.util.Properties;

@Testcontainers
public class KafkaTestSettings {

    @Container
    KafkaContainer kafkaContainer = new KafkaContainer(DockerImageName.parse("apache/kafka-native:4.0.2"));

    KafkaProducer<String, String> kafkaProducer;
    KafkaConsumer<String, String> kafkaConsumer;
    AdminClient adminClient;

    @BeforeEach
    public void setUp () {
        String bootStrapService = kafkaContainer.getBootstrapServers();
        Properties adminProperties = new Properties();
        adminProperties.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapService); //настройка админа кафки, куда подключается, для создания топиков

        adminClient = AdminClient.create(adminProperties);

        Properties kafkaConfigProperties = new Properties();
        kafkaConfigProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapService);
        kafkaConfigProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaConfigProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaConfigProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "test-group");
        kafkaConfigProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        kafkaConsumer = new KafkaConsumer<>(kafkaConfigProperties);

        Properties kafkaProducerConfig = new Properties();
        kafkaProducerConfig.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootStrapService);
        kafkaProducerConfig.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        kafkaProducerConfig.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        kafkaProducer = new KafkaProducer<>(kafkaProducerConfig);
    }

    public void createTopic(String topicName, int numPartitions, int replica) {
        adminClient.createTopics(Collections.singletonList(new NewTopic(topicName, numPartitions, (short) replica)));
    }

}
