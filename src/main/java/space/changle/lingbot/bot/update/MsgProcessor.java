package space.changle.lingbot.bot.update;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/18
 * @time 09:38
 * @description 更新处理器
 */
public interface MsgProcessor {

    boolean supports(Update update);

    void process(TelegramClient telegramClient, Object update);
}
