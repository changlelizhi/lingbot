package space.changle.lingbot.user.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import space.changle.lingbot.auth.UserRole;
import space.changle.lingbot.user.service.enums.UserStatus;

/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/14
 * @time 17:14
 * @description 用户实体类
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    private Long userId;

    private UserStatus status;

    private UserRole role;
}
