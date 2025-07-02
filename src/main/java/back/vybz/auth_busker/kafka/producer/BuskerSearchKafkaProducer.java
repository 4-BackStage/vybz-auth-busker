package back.vybz.auth_busker.kafka.producer;

import back.vybz.auth_busker.kafka.event.BuskerSearchEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuskerSearchKafkaProducer {

    private final KafkaTemplate<String, BuskerSearchEvent> kafkaTemplate;
    public static final String TOPIC_NAME = "create-busker-search";

    public void send(BuskerSearchEvent event){
        log.info("[Kafka] sending BuskerSearchEvent to topic '{}': {}", TOPIC_NAME, event);

        CompletableFuture<SendResult<String, BuskerSearchEvent>> future =
                kafkaTemplate.send(TOPIC_NAME, event.getBuskerUuid(), event);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("[Kafka] Failed to send BuskerSearchEvent: {}", ex.getMessage(), ex);
            } else {
                log.info("[Kafka] Successfully sent BuskerSearchEvent with offset: {}", result.getRecordMetadata().offset());
            }
        });
    }
}
