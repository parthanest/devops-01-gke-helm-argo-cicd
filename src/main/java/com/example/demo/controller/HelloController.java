package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.Map;

/**
 * A simple REST controller exposing a couple of endpoints so the
 * container has something meaningful to serve once deployed to GKE.
 */
@RestController
public class HelloController {

    /**
     * Root greeting endpoint.
     */
    @GetMapping("/")
    public Map<String, Object> hello() {
        return Map.of(
                "message", "Hello from the secure CI/CD pipeline on GKE!",
                "service", "demo-app",
                "timestamp", Instant.now().toString()
        );
    }

    /**
     * Lightweight version endpoint. In a real service the version would be
     * injected at build time (e.g. via the Git SHA passed to Jib).
     */
    @GetMapping("/version")
    public Map<String, String> version() {
        String sha = System.getenv().getOrDefault("APP_VERSION", "dev");
        return Map.of("version", sha);
    }
}
