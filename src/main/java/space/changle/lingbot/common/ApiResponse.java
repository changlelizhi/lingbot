package space.changle.lingbot.common;

import java.time.Instant;

/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/13
 * @time 20:39
 * @description api响应
 */
public record ApiResponse<T>(int code, String message, T data, long timestamp) {


    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(Result.SUCCESS.getCode(), Result.SUCCESS.getMessage(), data, Instant.now().toEpochMilli());
    }

    public static <T> ApiResponse<T> fail(Result result) {
        return new ApiResponse<>(result.getCode(), result.getMessage(), null, Instant.now().toEpochMilli());
    }

    public static <T> ApiResponse<T> fail(Result result, T data) {
        return new ApiResponse<>(result.getCode(), result.getMessage(), data, Instant.now().toEpochMilli());
    }

}
