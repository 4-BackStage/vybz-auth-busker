package back.vybz.auth_busker.kafka.config;

import back.vybz.auth_busker.kafka.event.BuskerSearchEvent;
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
public class BuskerSearchKafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public Map<String, Object> buskerSearchProducerConfigs() {
        Map<String, Object> props = new HashMap<>();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    @Bean
    public ProducerFactory<String, BuskerSearchEvent> createBuskerSearchNotification(){
        return new DefaultKafkaProducerFactory<>(buskerSearchProducerConfigs());
    }

    @Bean
    public KafkaTemplate<String, BuskerSearchEvent> buskerSearchKafkaTemplate() {
        return new KafkaTemplate<>(createBuskerSearchNotification());
    }

    @Bean
    public NewTopic buskerSearchTopic() {
        return new NewTopic("create-busker-search", 1, (short) 3);
    }
}
