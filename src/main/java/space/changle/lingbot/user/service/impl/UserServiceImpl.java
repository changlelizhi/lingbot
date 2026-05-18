package space.changle.lingbot.user.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import space.changle.lingbot.auth.TelegramAuth;
import space.changle.lingbot.common.Result;
import space.changle.lingbot.common.TmaUser;
import space.changle.lingbot.common.exception.LingBotException;
import space.changle.lingbot.dto.TmaUserSignInOutDto;
import space.changle.lingbot.user.entity.UserEntity;
import space.changle.lingbot.user.constant.UserConstants;
import space.changle.lingbot.user.mapper.UserMapper;
import space.changle.lingbot.user.service.UserService;

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

    public UserServiceImpl(TelegramAuth telegramAuth, UserMapper userMapper, StringRedisTemplate stringRedisTemplate) {
        this.telegramAuth = telegramAuth;
        this.userMapper = userMapper;
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public TmaUserSignInOutDto tmaUserSignIn(String tmaInitData) {
        // 检查
        if (StringUtils.isBlank(tmaInitData)) {
            throw  new LingBotException(Result.AUTH_INIT_MISSING_PARAM);
        }
        //校验
        telegramAuth.validateInitData(tmaInitData);
        TmaUser tmaUser = telegramAuth.getUser(tmaInitData);
        //redis查询用户是否注册 ，未注册先增数据库再增redis，注册过的直接发送token
        String userKey =UserConstants.USER_TOKEN_KEY + tmaUser.getUserId();
        String cacheValue = stringRedisTemplate.opsForValue().get(userKey);
        UserEntity user =null;
        if (StringUtils.isBlank(cacheValue)){
            //缓存未命中，查询数据库
            user=  userMapper.selectByUserId(tmaUser.getUserId());
            if (Objects.isNull( user)){
                // 插入新用户（利用唯一索引防重）
                try {
                    user=UserEntity.builder().build();
                    userMapper.insertUser(user);
                }catch (DuplicateKeyException e){
                    user = userMapper.selectByUserId(tmaUser.getUserId());
                }
            }

            //存入redis 缓存
            stringRedisTemplate.opsForValue().set(userKey, String.valueOf(tmaUser.getUserId()));
        }
        //生成 token


        //可选 ：存储 token -> userId 映射（用于主动踢人）


        return new TmaUserSignInOutDto("token");
    }
}
