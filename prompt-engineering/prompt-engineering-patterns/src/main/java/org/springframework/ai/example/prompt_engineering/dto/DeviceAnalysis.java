package org.springframework.ai.example.prompt_engineering.dto;

import java.util.List;

/**
 * 设备诊断结果实体 (Record)
 * * @param severity          严重等级 (INFO, WARNING, CRITICAL) - 枚举值的字符串形式
 * @param rootCause         根本原因分析 (技术语言)
 * @param readableSummary   给用户看的人话 (通俗语言)
 * @param actionItems       建议操作步骤 (List)
 * @param technicianRequired 是否需要派单上门
 */
public record DeviceAnalysis(
    String severity,
    String rootCause,
    String readableSummary,
    List<String> actionItems,
    boolean technicianRequired
) {}