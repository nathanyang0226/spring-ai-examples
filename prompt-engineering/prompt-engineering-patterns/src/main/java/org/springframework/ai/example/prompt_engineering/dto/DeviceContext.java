package org.springframework.ai.example.prompt_engineering.dto;

public record DeviceContext(String deviceId, String firmware, String location, String lastErrorCode) {}