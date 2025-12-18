package org.springframework.ai.example.prompt_engineering.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import java.util.function.Function;

@Configuration
public class IotToolsConfig {

    // 工具 1: 检查设备实时信号强度 (RSSI)
    // AI 会在觉得自己需要知道信号强度时，自动调用这个函数
    @Bean
    @Description("Check the Wi-Fi signal strength (RSSI) for a specific deviceId")
    public Function<DeviceRequest, SignalResponse> checkSignalStrength() {
        return request -> {
            // 模拟调用底层 IoT 平台接口
            System.out.println("🔧 AI 正在调用工具检查信号: " + request.deviceId());
            int rssi = -75; // 模拟弱信号
            return new SignalResponse(rssi, rssi < -70 ? "WEAK" : "GOOD");
        };
    }

    // 工具 2: 执行远程重启
    @Bean
    @Description("Send a remote reboot command to the device")
    public Function<DeviceRequest, String> remoteReboot() {
        return request -> {
            System.out.println("🔧 AI 正在执行远程重启: " + request.deviceId());
            return "Reboot command sent successfully. Waiting for device online...";
        };
    }
    
    // 辅助 Record
    public record DeviceRequest(String deviceId) {}
    public record SignalResponse(int rssi, String quality) {}
}