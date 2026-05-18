package space.changle.lingbot.bot.service.impl;

import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import space.changle.lingbot.bot.service.MessageService;

/**
 * @author 长乐
 * @date 2026/5/17
 * @time 12:13
 * @description
 */
public class MessageServiceImpl implements MessageService {
    @Override
    public void sendHtmlMessage(TelegramClient telegramClient, String chatId, String message) {

    }

    @Override
    public void sendStartCommandMessage(TelegramClient telegramClient, Long chatId) {
        WebAppInfo webAppInfo = WebAppInfo.builder().url("https://changle.space").build();



        //SendMessage.builder().chatId(chatId).parseMode(ParseMode.HTML).replyMarkup()

    }
}
