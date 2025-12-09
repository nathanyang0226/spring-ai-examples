package org.springframework.ai.example.prompt_engineering.dto;

public record DeviceDiagnosis(String status, String reason, boolean shutdownRecommended) {}