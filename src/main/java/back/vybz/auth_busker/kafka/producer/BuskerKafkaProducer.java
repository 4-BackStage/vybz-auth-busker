package back.vybz.auth_busker.kafka.producer;

import back.vybz.auth_busker.kafka.event.BuskerAuthEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class BuskerKafkaProducer {

    private final KafkaTemplate<String, BuskerAuthEvent> kafkaTemplate;

    public static final String CREATE_BUSKER_TOPIC = "create-busker-auth";

    public void sendBuskerAuthEvent(BuskerAuthEvent event) {
        log.info("[Kafka] Sending BuskerAuthEvent to topic '{}': {}", CREATE_BUSKER_TOPIC, event);
        CompletableFuture<SendResult<String, BuskerAuthEvent>> future =
                kafkaTemplate.send(CREATE_BUSKER_TOPIC, event);

        future.whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("[Kafka] Failed to send BuskerAuthEvent: {}", ex.getMessage(), ex);
            } else {
                log.info("[Kafka] Successfully sent BuskerAuthEvent with offset: {}", result.getRecordMetadata().offset());
            }
        });
    }
}
