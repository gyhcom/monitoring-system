package gyh.monitoring.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class HealthCheckProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public HealthCheckProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendAlert(String serverName) {
        kafkaTemplate.send("health-alerts", "ALERT:" + serverName + " is DOWN!");
    }
}
