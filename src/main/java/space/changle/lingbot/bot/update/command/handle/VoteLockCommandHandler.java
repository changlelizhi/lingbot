package space.changle.lingbot.bot.update.command.handle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import space.changle.lingbot.bot.LingBotCommand;
import space.changle.lingbot.bot.LingBotConstant;
import space.changle.lingbot.bot.update.command.CommandHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/19
 * @time 08:10
 * @description
 */
@Slf4j
@Component
public class VoteLockCommandHandler implements CommandHandler {

    private final StringRedisTemplate stringRedisTemplate;

    public VoteLockCommandHandler(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public String command() {
        return LingBotCommand.VOTE_LOCK.getCommand()+ LingBotConstant.BOT_NAME;
    }

    @Override
    public void handle(TelegramClient telegramClient, Update update) {
        log.info("处理投票解锁");
        //todo
    }
}
