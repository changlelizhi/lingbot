package space.changle.lingbot.auth;

import lombok.Getter;

/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/19
 * @description 登录类型
 */
@Getter
public enum LoginType {

    TMA("TMA"),
    WEB("WEB");

    private final String authority;

    LoginType(String authority) {
        this.authority = authority;
    }

}
