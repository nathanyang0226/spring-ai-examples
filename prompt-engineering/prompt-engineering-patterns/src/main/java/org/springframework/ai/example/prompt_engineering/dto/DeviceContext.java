package org.springframework.ai.example.prompt_engineering.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * 设备上下文对象
 * 用途：聚合分散的设备元数据，序列化成 JSON 后注入到 AI 的 Prompt 中
 */
// 1. 使用 @JsonInclude 过滤掉 null 值，节省 Token
@JsonInclude(JsonInclude.Include.NON_NULL) 
public record DeviceContext(
    String deviceId,          // 设备唯一标识
    String modelName,         // 型号 (如 "Ring_Cam_Pro")
    String firmwareVersion,   // 固件版本
    String location,          // 安装位置
    String connectivityType,  // 连接方式 (Wi-Fi / Zigbee / 5G)
    Integer signalStrength,   // 信号强度 (可能为 null)
    String lastErrorCode      // 最近一次报错代码
) {
    // Record 会自动生成 Getter, Constructor, toString, equals, hashCode
    // 你不需要写任何样板代码！
}