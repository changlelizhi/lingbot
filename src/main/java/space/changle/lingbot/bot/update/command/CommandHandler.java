package space.changle.lingbot.bot.update.command;

import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/18
 * @time 19:55
 * @description 命令处理器
 */
public interface CommandHandler {

    String command();

    void handle(TelegramClient telegramClient, Update update);

}
