package space.changle.lingbot.bot.service;

import org.telegram.telegrambots.meta.generics.TelegramClient;

/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/17
 * @time 12:03
 * @description 消息服务
 */
public interface MessageService {

    void sendHtmlMessage(TelegramClient telegramClient, Long chatId, String message);

    void sendStartCommandMessage(TelegramClient telegramClient, Long chatId);
}
