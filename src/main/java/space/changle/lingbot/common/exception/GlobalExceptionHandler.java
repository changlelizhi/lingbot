package space.changle.lingbot.common.exception;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import space.changle.lingbot.common.ApiResponse;
import space.changle.lingbot.common.Result;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/14
 * @time 12:42
 * @description 全局异常处理
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(LingBotException.class)
    public ApiResponse<Result> handleLingBotException(LingBotException ex) {
        return ApiResponse.fail(ex.getResult());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ApiResponse<?> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.put(error.getField(), error.getDefaultMessage())
        );
        return ApiResponse.fail(Result.FAIL, errors);
    }

}
