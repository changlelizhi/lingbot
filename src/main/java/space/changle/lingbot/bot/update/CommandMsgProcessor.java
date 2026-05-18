package space.changle.lingbot.bot.update;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import space.changle.lingbot.bot.update.command.CommandDispatcher;

/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/18
 * @time 09:55
 * @description 命令消息处理器
 */
@Component
public class CommandMsgProcessor implements MsgProcessor {

    private final CommandDispatcher commandDispatcher;

    public CommandMsgProcessor(CommandDispatcher commandDispatcher) {
        this.commandDispatcher = commandDispatcher;
    }

    @Override
    public boolean supports(Update update) {
        return update.hasMessage() && update.getMessage().isCommand();
    }

    @Override
    public void process(TelegramClient telegramClient, Update update) {
        commandDispatcher.dispatch(telegramClient, update);
    }
}
