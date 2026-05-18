package space.changle.lingbot.bot.update.command.handle;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import space.changle.lingbot.bot.LingBotCommand;
import space.changle.lingbot.bot.service.MessageService;
import space.changle.lingbot.bot.update.command.CommandHandler;

/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/18
 * @time 22:00
 * @description GitHub 命令处理器
 */
@Component
public class GitHubCommandHandler implements CommandHandler {

    private final MessageService messageService;

    public GitHubCommandHandler(MessageService messageService) {
        this.messageService = messageService;
    }

    @Override
    public String command() {
        return LingBotCommand.GITHUB_URL.getCommand();
    }

    @Override
    public void handle(TelegramClient telegramClient, Update update) {
        if (!update.getMessage().isUserMessage()) {
            return;
        }
        String message = """
                <a href="https://github.com/changlelizhi/lingbot">绫娘Bot</a>""";
        messageService.sendHtmlMessage(telegramClient, update.getMessage().getChatId(), message);
    }
}
