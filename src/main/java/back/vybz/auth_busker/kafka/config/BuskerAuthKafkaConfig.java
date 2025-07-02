package back.vybz.auth_busker.kafka.config;

import back.vybz.auth_busker.kafka.event.BuskerAuthEvent;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class BuskerAuthKafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> buskerAuthProducerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, BuskerAuthEvent> createBuskerAuthNotification() {
        return new DefaultKafkaProducerFactory<>(buskerAuthProducerConfigs());
    }

    @Bean
    public KafkaTemplate<String, BuskerAuthEvent> kafkaTemplate() {
        return new KafkaTemplate<>(createBuskerAuthNotification());
    }

    @Bean
    public NewTopic buskerAuthTopic() {
        return new NewTopic("create-busker-auth", 1, (short) 3);
    }
}
