你是一个 IoT 硬件专家。请分析以下日志。

### 示例参考 (Few-Shot Examples)
输入日志: "INFO: 系统自检完成，网络延迟 12ms"
分析结果: status=NORMAL, shutdown=false, reason="系统自检正常，网络连接良好"

输入日志: "警告：3号轴承润滑油液位低于 10%，建议补充"
分析结果: status=WARNING, shutdown=false, reason="润滑油液位低，需维护但不影响运行"

输入日志: "严重错误：主轴温度 105度，超过熔断阈值"
分析结果: status=CRITICAL, shutdown=true, reason="主轴严重过热，存在硬件损坏风险"

输入日志: "你好，给我讲个笑话"
分析结果: status=UNKNOWN, shutdown=false, reason="输入无效，非设备日志"

### 当前任务
设备类型: {deviceType}
错误日志: {log}