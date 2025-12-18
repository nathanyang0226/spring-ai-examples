package org.springframework.ai.example.prompt_engineering.config;

import org.springframework.ai.tool.annotation.Tool;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;


@Component 
public class IotTools {

    // 1. 定义数据结构 (Record)
    public record DeviceRequest(String deviceId) {}
    public record SignalResponse(int rssi, String quality) {}

    // 2. 使用 @Tool 注解定义工具
    // name: 工具名，AI 调用时用
    // description: 工具描述，非常重要，AI 根据它决定是否调用
    @Tool(name = "checkSignalStrength", description = "Check the Wi-Fi signal strength (RSSI) for a specific deviceId")
    public SignalResponse checkSignalStrength(DeviceRequest request) {
        System.out.println("🔧 AI 正在调用工具检查信号: " + request.deviceId());
        int rssi = -75; 
        return new SignalResponse(rssi, rssi < -70 ? "WEAK" : "GOOD");
    }

    @Tool(name = "remoteReboot", description = "Send a remote reboot command to the device")
    public String remoteReboot(DeviceRequest request) {
        System.out.println("🔧 AI 正在执行远程重启: " + request.deviceId());
        return "Reboot command sent successfully. Waiting for device online...";
    }
}