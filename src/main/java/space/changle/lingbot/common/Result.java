package space.changle.lingbot.common;

import lombok.Getter;

/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/13
 * @time 21:01
 * @description 结果枚举
 */
@Getter
public enum Result {

    AUTH_INIT_MISSING_PARAM(10000,"缺少初始化参数"),

    AUTH_INIT_EXPIRED(10001,"初始化参数已过期"),

    INVALID_SIGNATURE(10002, "无效的签名" ),

    TOKEN_MISSING(10003, "缺少Token"),

    TOKEN_EXPIRED(10004, "token已过期"),

    TOKEN_INVALID(10005, "token无效"),

    FORBIDDEN(10403, "权限不足"),

    SUCCESS(20000, "操作成功"),


    FAIL(50000, "操作失败"),

    USER_BANNED(30001, "该账号已被封禁");



    private final int code;

    private final String message;

    Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public static Result getByCode(int code) {
        for (Result value : values()) {
            if (value.code == code) {
                return value;
            }
        }
        return null;
    }

}
