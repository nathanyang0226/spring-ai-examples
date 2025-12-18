package org.springframework.ai.example.prompt_engineering.dto;

// Spring Boot 3+ (Jakarta EE 9+) 使用 jakarta 包名
import jakarta.validation.constraints.NotBlank; 

/**
 * 前端请求 DTO (Data Transfer Object)
 * 接收用户提交的诊断请求
 */
public record DiagnosisRequest(
    
    // 1. 必填项：设备唯一标识
    @NotBlank(message = "Device ID cannot be empty")
    String deviceId,

    // 2. 必填项：用户的主诉 (Complaint)
    @NotBlank(message = "User complaint is required")
    String complaint,

    // 3. 选填项：设备位置
    String location,

    // 4. 选填项：固件版本
    String firmware
) {}