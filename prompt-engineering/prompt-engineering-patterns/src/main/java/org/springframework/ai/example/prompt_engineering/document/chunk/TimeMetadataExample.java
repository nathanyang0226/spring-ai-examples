package org.springframework.ai.example.prompt_engineering.document.chunk;

import org.springframework.ai.document.Document;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * 时间字段建议用 ISO 8601 格式（2024-04-01T00:00:00），这是国际标准，各种编程语言和数据库都能正确解析。
 */
public class TimeMetadataExample {

    public static Document createChunkWithTime(
            String content,
            LocalDateTime createdAt,
            LocalDateTime effectiveDate,
            LocalDateTime expirationDate) {

        Map<String, Object> metadata = new HashMap<>();

        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        metadata.put("created_at", createdAt.format(formatter));

        if (effectiveDate != null) {
            metadata.put("effective_date", effectiveDate.format(formatter));
        }

        if (expirationDate != null) {
            metadata.put("expiration_date", expirationDate.format(formatter));
        }

        return new Document(content, metadata);
    }

    public static void main(String[] args) {
        String chunkContent = "员工差旅住宿费用报销上限：一线城市 500 元/晚，二线城市 300 元/晚。";

        Document chunk = createChunkWithTime(
            chunkContent,
            LocalDateTime.now(),
            LocalDateTime.of(2026, 1, 1, 8, 0),
            LocalDateTime.of(2028, 12, 31, 23, 59)
        );

        System.out.println("Chunk content: " + chunk.getText());
        System.out.println("Effective from: " + chunk.getMetadata().get("effective_date"));
        System.out.println("Expires at: " + chunk.getMetadata().get("expiration_date"));
    }
}