package space.changle.lingbot.user.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import space.changle.lingbot.auth.LoginType;
import space.changle.lingbot.auth.TelegramAuth;
import space.changle.lingbot.auth.UserRole;
import space.changle.lingbot.auth.jwt.JwtUtil;
import space.changle.lingbot.common.Result;
import space.changle.lingbot.common.TmaUser;
import space.changle.lingbot.common.exception.LingBotException;
import space.changle.lingbot.dto.CheckUserOutDto;
import space.changle.lingbot.dto.TmaLoginOutDto;
import space.changle.lingbot.user.entity.UserEntity;
import space.changle.lingbot.user.constant.UserConstants;
import space.changle.lingbot.user.mapper.UserMapper;
import space.changle.lingbot.user.service.UserService;
import space.changle.lingbot.user.service.enums.UserStatus;

import java.util.Objects;

/**
 * @author 长乐
 * @date 2026/5/13
 * @time 21:57
 * @description
 */
@Service
public class UserServiceImpl implements UserService {

    private final TelegramAuth telegramAuth;

    private final UserMapper userMapper;

    private final StringRedisTemplate stringRedisTemplate;

    private final JwtUtil jwtUtil;

    public UserServiceImpl(TelegramAuth telegramAuth, UserMapper userMapper, StringRedisTemplate stringRedisTemplate, JwtUtil jwtUtil) {
        this.telegramAuth = telegramAuth;
        this.userMapper = userMapper;
        this.stringRedisTemplate = stringRedisTemplate;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public TmaLoginOutDto tmaUserSignIn(String tmaInitData) {
        // 检查
        if (StringUtils.isBlank(tmaInitData)) {
            throw new LingBotException(Result.AUTH_INIT_MISSING_PARAM);
        }
        //校验
        telegramAuth.validateInitData(tmaInitData);
        TmaUser tmaUser = telegramAuth.getUser(tmaInitData);
        Long userId = tmaUser.getUserId();

        String banKey = UserConstants.USER_BAN_KEY + userId;
        if (stringRedisTemplate.hasKey(banKey)) {
            throw new LingBotException(Result.USER_BANNED);
        }

        String activeKey = UserConstants.USER_ACTIVE_KEY + userId;
        UserEntity user = null;
        if (stringRedisTemplate.hasKey(activeKey)) {
            user = userMapper.selectByUserId(userId);
        }else {
            user = userMapper.selectByUserId(userId);
            if (Objects.isNull(user)) {
                try {
                    user = UserEntity.builder()
                            .userId(userId)
                            .role(UserRole.USER)
                            .status(UserStatus.ACTIVE)
                            .build();
                    userMapper.insertUser(user);
                } catch (DuplicateKeyException e) {
                    user = userMapper.selectByUserId(userId);
                }

            }
            stringRedisTemplate.opsForValue().set(activeKey, String.valueOf(userId));
        }
        if (user != null && user.getStatus() == UserStatus.BANNED) {
            throw new LingBotException(Result.USER_BANNED);
        }
        UserRole role = user != null && user.getRole() != null ? user.getRole() : UserRole.USER;
        String token = jwtUtil.createToken(String.valueOf(userId), LoginType.TMA, role);
        return new TmaLoginOutDto(token);
    }

    @Override
    public CheckUserOutDto isUserRegistered(String initData) {
        if (StringUtils.isBlank(initData)) {
            throw new LingBotException(Result.AUTH_INIT_MISSING_PARAM);
        }
        telegramAuth.validateInitData(initData);
        TmaUser tmaUser = telegramAuth.getUser(initData);
        Long userId = tmaUser.getUserId();

        String banKey = UserConstants.USER_BAN_KEY + userId;
        if (stringRedisTemplate.hasKey(banKey)) {
            throw new LingBotException(Result.USER_BANNED);
        }
        String activeKey = UserConstants.USER_ACTIVE_KEY + userId;
        if (stringRedisTemplate.hasKey(activeKey)) {
            return new CheckUserOutDto(true, false);
        }

        UserEntity user = userMapper.selectByUserId(userId);
        if (Objects.nonNull(user)) {
            String key = user.getStatus() == UserStatus.BANNED ? banKey : activeKey;
            stringRedisTemplate.opsForValue().set(key, String.valueOf(userId));

            return new CheckUserOutDto(true, true);
        }

        return new CheckUserOutDto(false, false);
    }
}
