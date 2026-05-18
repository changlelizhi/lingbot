package space.changle.lingbot.bot.update;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.util.List;

/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/18
 * @time 09:40
 * @description 命令分发器
 */
@Component
public class MsgDispatcher {

    private final List<MsgProcessor> msgProcessors;


    public MsgDispatcher(List<MsgProcessor> msgProcessors) {
        this.msgProcessors = msgProcessors;
    }

    public void dispatch(TelegramClient telegramClient, Update update) {
        for (MsgProcessor msgProcessor : msgProcessors) {
            if (msgProcessor.supports(update)) {
                msgProcessor.process(telegramClient, update);
            }
        }
    }
}
