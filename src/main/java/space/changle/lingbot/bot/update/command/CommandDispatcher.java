package space.changle.lingbot.bot.update.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/18
 * @time 19:55
 * @description 命令分发器
 */
@Component
public class CommandDispatcher {

    private final CommandRegistry commandRegistry;

    public CommandDispatcher(CommandRegistry commandRegistry) {
        this.commandRegistry = commandRegistry;
    }
    public void dispatch(TelegramClient telegramClient, Update update) {
        if (update.hasMessage() && update.getMessage().isCommand()){
            String command = update.getMessage().getText();
            CommandHandler commandHandler = commandRegistry.getCommandHandler(command);
            if (commandHandler != null) {
                commandHandler.handle(telegramClient, update);
            }
        }
    }

}
