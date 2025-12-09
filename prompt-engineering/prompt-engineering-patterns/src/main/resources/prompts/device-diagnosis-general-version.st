# ==========================================
# 你是一个拥有20年经验的 IoT 硬件专家。
#  请根据以下错误日志，分析设备状态。
#  如果输入不是日志，请返回状态 UNKNOWN

#  【重要】status 字段只能从以下四个单词中选一个：NORMAL, WARNING, CRITICAL，UNKNOWN

#  设备类型: {deviceType}
#  错误日志: {log}
# ==========================================

# 版本 B: 生产版 (当前生效，因为没加#)
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

# ==========================================