package space.changle.lingbot.bot.update;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/18
 * @time 09:55
 * @description 命令消息处理器
 */
@Component
public class CommandMsgProcessor implements MsgProcessor {


    @Override
    public boolean supports(Update update) {

      //  update.hasMessage() && update.getMessage().isCommand() && update.getMessage()
        return update.hasMessage() && update.getMessage().isCommand();
    }

    @Override
    public void process(TelegramClient telegramClient, Object update) {

    }
}
