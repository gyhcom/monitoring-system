package gyh.monitoring.controller;

import gyh.monitoring.service.HealthCheckProducer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthCheckController {
    private HealthCheckProducer healthCheckProducer;

    public HealthCheckController(HealthCheckProducer healthCheckProducer) {
        this.healthCheckProducer = healthCheckProducer;
    }

    @GetMapping
    public ResponseEntity<String> checkHealth() {
        return ResponseEntity.ok("Server is UP");
    }

    @PostMapping("/simulate-down")
    public ResponseEntity<String> simulateDown() {
        healthCheckProducer.sendAlert("Server-1");
        return ResponseEntity.ok("Simulated server down event sent");
    }

}
