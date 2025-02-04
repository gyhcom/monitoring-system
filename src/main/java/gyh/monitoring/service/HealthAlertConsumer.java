package gyh.monitoring.service;

import gyh.monitoring.repository.RedisPublisher;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class HealthAlertConsumer {
    private final RedisPublisher redisPublisher;

    public HealthAlertConsumer(RedisPublisher redisPublisher) {
        this.redisPublisher = redisPublisher;
    }

    @KafkaListener(topics = "health-alerts", groupId = "monitoring-group")
    public void handleAlert(String message) {
        System.out.println("장애 감지: " + message);
        List<String> criticalAlerts = Stream.of(message)
                .filter(msg -> msg.toLowerCase().contains("down"))
                .map(String::toUpperCase)
                .collect(Collectors.toList());

    criticalAlerts.forEach(alert -> System.out.println("장애 이벤트 처리: "+alert));

    criticalAlerts.forEach(alert ->{
        String serverName = extractServerName(alert);
        redisPublisher.saveHealthStatus("server-health:" + serverName, "DOWN");
        System.out.println("Redis 저장 완료: " + serverName);
    });

        criticalAlerts.forEach(alert -> {
            String serverName = extractServerName(alert);
            redisPublisher.publishHealthStatus(serverName, false);
        });
    }

    private String extractServerName(String message) {
        return message.split(" ")[1];
    }

}
