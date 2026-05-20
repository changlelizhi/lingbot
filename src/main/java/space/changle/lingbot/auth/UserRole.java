package space.changle.lingbot.auth;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/19
 * @time 18:03
 * @description 用户角色枚举
 */
@Getter
public enum UserRole {

    USER,
    REVIEWER,
    BAN,
    ADMIN;

    public String authority() {
        return "ROLE_" + name();
    }

    public List<String> authorities() {
        List<String> list = new ArrayList<>();
        list.add(USER.authority());
        switch (this) {
            case ADMIN -> {
                list.add(REVIEWER.authority());
                list.add(BAN.authority());
                list.add(ADMIN.authority());
            }
            case REVIEWER -> list.add(REVIEWER.authority());
            case BAN -> list.add(BAN.authority());
            // USER already added above
        }
        return list;
    }

}
