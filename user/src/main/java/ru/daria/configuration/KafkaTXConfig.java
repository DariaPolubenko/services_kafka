//package ru.daria.user_service.configuration;
//
//import org.apache.kafka.clients.producer.ProducerConfig;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.kafka.core.DefaultKafkaProducerFactory;
//import org.springframework.kafka.core.ProducerFactory;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class KafkaTXConfig {  //exactly ones
//    @Bean
//    public ProducerFactory<String, String> txProducerFactory() {
//        Map<String, Object> props = new HashMap<>();
//        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
//        props.put(ProducerConfig.TRANSACTIONAL_ID_CONFIG, "my-tx-producer");
//        props.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true); //включает идемпотентность (не переписывает результат при дублировани)
//        props.put(ProducerConfig.ACKS_CONFIG, "all"); // проверяет приходили ли до данные или нет
//        props.put(ProducerConfig.RETRIES_CONFIG, Integer.MAX_VALUE);
//        props.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION, 5);
//
//        DefaultKafkaProducerFactory<String, String> factory =
//                new DefaultKafkaProducerFactory<>(props);
//        factory.setTransactionIdPrefix("tx-");
//        return factory;
//    }
//}
