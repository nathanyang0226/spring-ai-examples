package org.springframework.ai.example.prompt_engineering.advice;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice; // 推荐用这个

import java.util.HashMap;
import java.util.Map;

// 1. 改为 @RestControllerAdvice，它包含了 @ControllerAdvice 和 @ResponseBody
// 这样你就不用担心忘记加 @ResponseBody 了
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        // 2. 使用 Map 来存储错误，而不是 StringBuilder
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        // 3. 直接返回 Map，Spring Boot 会自动把它转成 JSON
        return ResponseEntity.badRequest().body(errors);
    }
}