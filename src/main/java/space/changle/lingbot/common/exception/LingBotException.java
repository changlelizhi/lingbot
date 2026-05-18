package space.changle.lingbot.common.exception;

import lombok.Getter;
import space.changle.lingbot.common.Result;

/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/14
 * @time 08:12
 * @description
 */
@Getter
public class LingBotException extends RuntimeException{

    private final Result result;

    public LingBotException(Result result) {
        super(result.getMessage());
        this.result = result;
    }
}
