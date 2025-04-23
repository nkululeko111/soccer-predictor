package org.soccer.smartbet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class SmartBetApplication {

    public static void main(String[] args) {
        SpringApplication.run(SmartBetApplication.class, args);
    }

    // Basic health check endpoint
    @RestController
    @RequestMapping("/api")
    public static class HealthController {

        @GetMapping("/health")
        public ResponseEntity<String> healthCheck() {
            return ResponseEntity.ok("SmartBet API is running");
        }
    }
}