package space.changle.lingbot.bot.update.command.handle;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import space.changle.lingbot.bot.LingBotCommand;
import space.changle.lingbot.bot.service.MessageService;
import space.changle.lingbot.bot.update.command.CommandHandler;

/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/18
 * @time 19:56
 * @description /start 命令处理器
 */
@Slf4j
@Component
public class StartCommandHandler implements CommandHandler {

    private final MessageService messageService;

    public StartCommandHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public String command() {
        return LingBotCommand.START.getCommand();
    }

    @Override
    public void handle(TelegramClient telegramClient, Update update) {
        if (!update.getMessage().isUserMessage()) {
            return;
        }
        messageService.sendStartCommandMessage(telegramClient, update.getMessage().getChatId());
    }
}
