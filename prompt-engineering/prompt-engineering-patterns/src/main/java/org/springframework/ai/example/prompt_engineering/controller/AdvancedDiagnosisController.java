package org.springframework.ai.example.prompt_engineering.controller;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.example.prompt_engineering.config.IotTools;
import org.springframework.ai.example.prompt_engineering.dto.DeviceContext;
import org.springframework.ai.example.prompt_engineering.dto.DiagnosisRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/iot/advanced-agent")
public class AdvancedDiagnosisController {

    private final ChatClient chatClient;

    private final IotTools iotTools;

    public AdvancedDiagnosisController(ChatClient.Builder builder, IotTools iotTools) {
        this.chatClient = builder
//                .defaultSystem("""
//                    你是一个高级 IoT 诊断 Agent。
//                    你的目标是解决用户的设备故障。
//
//                    【思考流程】
//                    1. 先分析用户描述。
//                    2. 如果怀疑是网络问题，请调用工具检查信号强度。
//                    3. 如果信号极差，建议用户调整路由器位置。
//                    4. 如果信号正常但设备无响应，尝试调用工具进行远程重启。
//                    5. 最后汇总你的操作和结果给用户。
//                    """)
                .build();
        this.iotTools = iotTools;

    }


    @PostMapping("/auto-fix")
    public String autoFix(@RequestBody String userComplaint) {
        return chatClient.prompt()
                .user(userComplaint)
                // 2. 这里传入包含 @Tool 方法的【实例对象】
                // Spring AI 会自动扫描这个对象里所有带 @Tool 的方法
                .tools(iotTools)
                .call()
                .content();
    }

    @PostMapping("/diagnose-specific-device")
    public String diagnoseSpecificDevice(
            @RequestParam String deviceId,  // 1. 前端传过来的 ID
            @RequestBody String userComplaint) { // 用户说的话

        return chatClient.prompt()
                // 2. 动态覆盖/增强 System Prompt
                .system(s -> s.text("""
                                你是一个高级 IoT 诊断 Agent。
                                你的目标是解决用户的设备故障。
                                
                                【思考流程】
                                1. 先分析用户描述。
                                2. 如果怀疑是网络问题，请调用工具检查信号强度。
                                3. 如果信号极差，建议用户调整路由器位置。
                                4. 如果信号正常但设备无响应，尝试调用工具进行远程重启。
                                5. 最后汇总你的操作和结果给用户。
                                
                                【当前上下文】
                                你正在诊断的设备ID是：{target_device_id}。
                                如果用户的问题涉及到设备操作，请直接使用这个ID，【不需要】再询问用户。
                                """)
                        .param("target_device_id", deviceId)) // 注入参数
                .user(userComplaint)
                .tools(iotTools) // 挂载工具
                .call()
                .content();
    }


    @PostMapping("/diagnose-rich-context")
    public String diagnoseWithRichContext(
            @RequestParam String deviceId,
            @RequestParam String firmwareVersion, // 新增：固件版本
            @RequestParam String location,        // 新增：安装位置
            @RequestBody String userComplaint) {

        return chatClient.prompt()
                .system(s -> s.text("""
                                你是一个高级 IoT 诊断 Agent。
                                
                                【设备上下文】
                                - 设备ID: {target_device_id}
                                - 固件版本: {fw_version}
                                - 安装位置: {install_location}
                                
                                【诊断规则】
                                1. 如果固件版本是 "v1.0"，它是老旧版本，通常建议用户先升级到 v2.0。
                                2. 如果安装位置是 "地下室" (Basement) 且用户反馈连接问题，优先怀疑 Wi-Fi 信号穿墙能力不足。
                                3. 如果设备操作需要，请直接使用上面的设备ID调用工具。
                                """)
                        // ✨ 链式调用注入多个参数
                        .param("target_device_id", deviceId)
                        .param("fw_version", firmwareVersion)
                        .param("install_location", location)
                )
                .user(userComplaint)
                .tools(iotTools) // 别忘了挂载工具
                .call()
                .content();
    }


    @PostMapping("/diagnose-by-bean")
    public String diagnoseByBean(@RequestBody @Validated DiagnosisRequest request) {

        // 1. 模拟从数据库/设备影子(Device Shadow)服务获取详细信息
        // 在真实项目中，这里会是 deviceService.getDeviceDetail(id)
        DeviceContext context = new DeviceContext(
                request.deviceId(),
                "SmartCam_Pro_V2", // 数据库查出来的型号`
                request.location(), // 前端传的位置信息
                "TIMEOUT_ERR"      // 最近的错误记录
        );

        return chatClient.prompt()
                .system(s -> s.text("""
                        你是一个高级 IoT 诊断 Agent。
                        
                        请参考以下设备运行时的上下文数据来进行诊断：
                        {context}
                        
                        【诊断规则】
                        - 如果 signalStrength 低于 -70 且 location 是地下室，提示加装放大器。
                        - 如果 firmwareVersion 低于 v2.0，提示升级。
                        """)
                        .param("context", context)) //springAI自动转JSON
                .user(request.complaint())
                .tools(iotTools)
                .call()
                .content();

    }
}