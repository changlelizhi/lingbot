package space.changle.lingbot.common.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/9
 * @time 14:12
 * @description json工具类
 */
@Slf4j
public class JsonUtil {

    private static final ObjectMapper OBJECT_MAPPER = createObjectMapper();

    private static ObjectMapper createObjectMapper() {
        return JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .defaultPropertyInclusion(JsonInclude.Value.construct(
                        JsonInclude.Include.NON_NULL,
                        JsonInclude.Include.ALWAYS))
                // 禁止时间戳格式
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                // 禁用持续时间时间戳格式
                .disable(SerializationFeature.WRITE_DURATIONS_AS_TIMESTAMPS)
                //禁用美化输出（线上环境使用）
                .disable(SerializationFeature.INDENT_OUTPUT)
                // 配置：允许未知属性
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .build();
    }

    /**
     * 将对象转换为 JSON 字符串
     *
     * @param obj
     * @return JSON字符串
     */
    public static String toJson(Object obj) {
        try {
            return OBJECT_MAPPER.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("JSON toJson error", e);
            throw new RuntimeException("JSON toJson error", e);
        }
    }

    /**
     * 将 JSON 字符串解析为指定类型的对象
     *
     * @param json JSON字符串
     * @param type 目标对象类型
     * @param <T> 目标对象类型
     * @return 对象
     */
    public static <T> T toClass(String json, Class<T> type) {
        try {
            return OBJECT_MAPPER.readValue(json, type);
        } catch (JsonProcessingException e) {
            log.error("JSON toClass error: {}", e.getMessage(), e);
            throw new RuntimeException("JSON toClass error", e);
        }
    }

    /**
     * 将对象转换为格式化的 JSON 字符串（美化 JSON）
     *
     * @param obj 对象
     * @return JSON字符串
     */
    public static String toPrettyJson(Object obj) {
        try {
            return OBJECT_MAPPER.writerWithDefaultPrettyPrinter()
                    .writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("JSON toPrettyJson error: {}", e.getMessage());
            throw new RuntimeException("JSON toPrettyJson error", e);
        }
    }

    /**
     * 将 JSON 字符串解析为复杂类型的 Java 对象
     *
     * @param json JSON字符串
     * @param typeReference 目标对象类型
     * @param <T> 目标对象类型
     * @return 对象
     */
    public static <T> T parseJson(String json, TypeReference<T> typeReference) {
        try {
            return OBJECT_MAPPER.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            log.error("JSON parseJson error: {}", e.getMessage());
            throw new RuntimeException("JSON parseJson error", e);
        }
    }

    /**
     * 将 JSON 字符串解析为 Jackson 的 JsonNode 对象
     *
     * @param json JSON字符串
     * @return JsonNode对象
     */
    public static JsonNode parseJsonNode(String json) {
        try {
            return OBJECT_MAPPER.readTree(json);
        } catch (JsonProcessingException e) {
            log.error("JSON parseJsonNode error: {}", e.getMessage());
            throw new RuntimeException("JSON parseJsonNode error", e);
        }
    }

}
