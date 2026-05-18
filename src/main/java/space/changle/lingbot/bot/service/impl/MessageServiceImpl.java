package space.changle.lingbot.bot.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardRow;
import org.telegram.telegrambots.meta.api.objects.webapp.WebAppInfo;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import space.changle.lingbot.bot.service.MessageService;

/**
 * @author 长乐
 * @date 2026/5/17
 * @time 12:13
 * @description
 */
@Slf4j
@Service
public class MessageServiceImpl implements MessageService {
    @Override
    public void sendHtmlMessage(TelegramClient telegramClient, Long chatId, String message) {
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .parseMode(ParseMode.HTML)
                .text(message)
                .build();
        try {
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("发送 HTML 消息失败: chatId={}", chatId, e);
        }


    }

    @Override
    public void sendStartCommandMessage(TelegramClient telegramClient, Long chatId) {
        WebAppInfo webAppInfo = WebAppInfo.builder().url("https://changle.space").build();
        InlineKeyboardButton webAppBtn = InlineKeyboardButton.builder()
                .webApp(webAppInfo)
                .text("点击进入")
                .build();
        InlineKeyboardRow row = new InlineKeyboardRow(webAppBtn);
        InlineKeyboardMarkup keyboardMarkup = InlineKeyboardMarkup.builder()
                .keyboardRow(row)
                .build();
        String welcomeText = """
                <b>欢迎来到绫娘 Bot!</b>
                
                点击下方按钮，开始探索吧。""";
        SendMessage sendMessage = SendMessage.builder()
                .chatId(chatId)
                .parseMode(ParseMode.HTML)
                .text(welcomeText)
                .replyMarkup(keyboardMarkup)
                .build();
        try {
            telegramClient.execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("发送 /start 欢迎消息失败: chatId={}", chatId, e);
        }


        //SendMessage.builder().chatId(chatId).parseMode(ParseMode.HTML).replyMarkup()

    }
}
