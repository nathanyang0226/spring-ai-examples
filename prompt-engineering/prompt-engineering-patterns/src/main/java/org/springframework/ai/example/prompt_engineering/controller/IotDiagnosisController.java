package org.springframework.ai.example.prompt_engineering.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.example.prompt_engineering.dto.DeviceAnalysis;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author yangjian
 * @date 2025/12/16
 * @Version 1.0
 * @Description TODO
 */

@RestController
@RequestMapping("/iot/agent")
public class IotDiagnosisController {


    private final ChatClient chatClient;

    public IotDiagnosisController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.defaultSystem("""
                你是一个智能家居 IOT 维修专家系统。
                你的任务是分析设备上传的错误日志，并输出结构化的诊断结果。
                
                【领域规则 Knowledge Base】
                
                1. 如果日志包含 "Overheat" 且温度 > 90°C，标记为 CRITICAL。
                
                2. 如果日志包含 "Network timeout" 或 "Retry"，标记为 WARNING。
                
                3. 如果日志包含 "Heartbeat" 或 "Info"，标记为 INFO。
                
                【输出要求】
                - rootCause 请用专业术语。
                - readableSummary 请用像对待没有任何技术背景的老奶奶说话一样的语气。
                - actionItems 必须是具体的步骤列表（如：1.拔掉电源...）。
                
                """).build();
    }


    /**
     * 核心接口：设备故障诊断
     * Input: 原始字符串日志
     * Output: 标准 JSON 对象
     */
    @PostMapping("/diagnose")
    public DeviceAnalysis diagonoseLog(@RequestBody String rawLog) {

        // 使用 Fluent API 调用 AI
        return chatClient.prompt().
                user(u -> u.text("分析以下设备日志: \n{log}")
                        .param("log", rawLog))
                .call()
                // 🪄 Magic Here: Spring AI 自动将 LLM 的文本响应映射为 Java Record
                .entity(DeviceAnalysis.class);
    }


//    /**
//     * 核心接口：设备故障诊断
//     * Input: 原始字符串日志
//     * Output: 标准 JSON 对象
//     */
//    @PostMapping("/diagnose")
//    public DeviceAnalysis diagnoseLog(@RequestBody String rawLog) {
//
//        // 使用 Fluent API 调用 AI
//        return chatClient.prompt()
//                .user(u -> u.text("分析以下设备日志：\n{log}")
//                        .param("log", rawLog))
//                .call()
//                // 🪄 Magic Here: Spring AI 自动将 LLM 的文本响应映射为 Java Record
//                .entity(DeviceAnalysis.class);
//    }
}
   