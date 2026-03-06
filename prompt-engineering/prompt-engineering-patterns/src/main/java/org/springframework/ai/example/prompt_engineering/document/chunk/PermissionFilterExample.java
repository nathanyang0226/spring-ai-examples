package org.springframework.ai.example.prompt_engineering.document.chunk;

import org.springframework.ai.document.Document;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 权限过滤的价值：
 * <p>
 * 数据安全：敏感信息不会泄露给无权限的用户
 * <p>
 * 合规要求：满足企业的信息安全和合规要求（如 GDPR、等保）
 * <p>
 * 用户体验：用户只看到和自己相关的信息，不会被无关内容干扰
 */
public class PermissionFilterExample {

    /**
     * 模拟向量数据库中的 chunks
     */
    private static List<Document> mockChunks() {
        List<Document> chunks = new ArrayList<>();

        // Chunk 1: 公开信息
        Map<String, Object> meta1 = new HashMap<>();
        meta1.put("sensitivity_level", "public");
        meta1.put("access_roles", Arrays.asList("employee"));
        chunks.add(new Document("员工手册规定，所有员工享有带薪年假。", meta1));

        // Chunk 2: 人事部内部信息
        Map<String, Object> meta2 = new HashMap<>();
        meta2.put("sensitivity_level", "confidential");
        meta2.put("access_departments", Arrays.asList("hr", "executive"));
        chunks.add(new Document("年终奖为月薪的 2-6 倍，根据绩效等级确定。", meta2));

        // Chunk 3: 技术部可见信息
        Map<String, Object> meta3 = new HashMap<>();
        meta3.put("sensitivity_level", "internal");
        meta3.put("access_departments", Arrays.asList("tech", "product"));
        chunks.add(new Document("技术部员工可申请远程办公，每周最多 2 天。", meta3));

        return chunks;
    }

    /**
     * 根据用户权限过滤 chunks
     */
    public static List<Document> filterByPermission(
            List<Document> chunks,
            String userRole,
            String userDepartment) {

        return chunks.stream()
            .filter(chunk -> hasPermission(chunk, userRole, userDepartment))
            .collect(Collectors.toList());
    }

    /**
     * 判断用户是否有权限访问某个 chunk
     */
    private static boolean hasPermission(
            Document chunk,
            String userRole,
            String userDepartment) {

        Map<String, Object> metadata = chunk.getMetadata();

        // 公开信息，所有人都能看
        String sensitivity = (String) metadata.get("sensitivity_level");
        if ("public".equals(sensitivity)) {
            return true;
        }

        // 检查角色权限
        List<String> accessRoles = (List<String>) metadata.get("access_roles");
        if (accessRoles != null && accessRoles.contains(userRole)) {
            return true;
        }

        // 检查部门权限
        List<String> accessDepts = (List<String>) metadata.get("access_departments");
        if (accessDepts != null && accessDepts.contains(userDepartment)) {
            return true;
        }

        return false;
    }

    public static void main(String[] args) {
        List<Document> allChunks = mockChunks();

        // 场景 1: 技术部普通员工
        System.out.println("=== 技术部员工小李能看到的内容 ===");
        List<Document> techResults = filterByPermission(allChunks, "employee", "tech");
        techResults.forEach(chunk -> System.out.println("- " + chunk.getText()));

        System.out.println("\n=== 人事部经理能看到的内容 ===");
        List<Document> hrResults = filterByPermission(allChunks, "manager", "hr");
        hrResults.forEach(chunk -> System.out.println("- " + chunk.getText()));
    }
}