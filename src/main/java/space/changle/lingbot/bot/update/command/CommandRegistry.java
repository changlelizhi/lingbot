package space.changle.lingbot.bot.update.command;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/18
 * @time 19:59
 * @description 命令注册
 */
@Slf4j
@Component
public class CommandRegistry {

    private final Map<String, CommandHandler> commandHandlerMap;

    public CommandRegistry(List<CommandHandler> commandHandlers) {
        Map<String, CommandHandler> commandMap =new HashMap<>();
        for (CommandHandler commandHandler : commandHandlers) {
            commandMap.put(commandHandler.command(), commandHandler);
        }
        this.commandHandlerMap = commandMap;
    }

    public CommandHandler getCommandHandler(String command) {
        return commandHandlerMap.get(command);
    }
}
