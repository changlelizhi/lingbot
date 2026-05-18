package space.changle.lingbot.bot;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.longpolling.BotSession;
import org.telegram.telegrambots.longpolling.interfaces.LongPollingUpdateConsumer;
import org.telegram.telegrambots.longpolling.starter.AfterBotRegistration;
import org.telegram.telegrambots.longpolling.starter.SpringLongPollingBot;
import org.telegram.telegrambots.longpolling.util.LongPollingSingleThreadUpdateConsumer;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScope;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeAllGroupChats;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeAllPrivateChats;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.TelegramClient;
import space.changle.lingbot.common.util.JsonUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/7
 * @time 21:47
 * @description 绫娘Bot
 */
@Slf4j
@Component
public class LingBot implements SpringLongPollingBot, LongPollingSingleThreadUpdateConsumer {

    @Value("${telegram.bot.token}")
    private String botToken;

    private BotSession botSession;

    private final TelegramClient telegramClient;


    public LingBot(TelegramClient telegramClient) {
        this.telegramClient = telegramClient;
    }


    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public LongPollingUpdateConsumer getUpdatesConsumer() {
        return this;
    }

    @Override
    public void consume(Update update) {
        log.info("收到消息：{}", JsonUtil.toPrettyJson(update));

    }

    /**
     * 启动后执行
     */
    @AfterBotRegistration
    public void afterRegistration(BotSession botSession) {
        this.botSession = botSession;  // 保存引用

        if (!botSession.isRunning()) {
            log.error("Bot 启动失败 ❌");
            return;
        }
        try {
            setBotCommons();
            log.info("Bot 启动成功 ✅");
        } catch (TelegramApiException e) {
            log.error("Bot 启动失败 ❌", e);
        }

    }

    /**
     * 定期检查 BotSession 状态并作出响应
     * 每 30 秒检查一次
     */
    @Scheduled(fixedDelay = 3 * 60 * 1000)
    public void monitorBotSession() throws TelegramApiException {
        if (botSession == null) {
            log.warn("BotSession 尚未初始化");
            return;
        }

        boolean running = botSession.isRunning();
        if (!running) {
            log.warn("⚠️ BotSession 已停止运行！");
            // 在这里实现你的应急措施——例如尝试重新注册、发送告警等、
            botSession.start();
        } else {
            log.info("BotSession 运行中 ✅");
        }
    }

    @PreDestroy
    public void cleanup() {
        if (botSession != null && botSession.isRunning()) {
            log.info("正在停止 BotSession...");
            botSession.stop();
            log.info("BotSession 已停止");
        }
    }

    /**
     * 设置bot命令
     */
    private void setBotCommons() throws TelegramApiException {
        List<BotCommand> privateCommands = new ArrayList<>();
        List<BotCommand> groupCommands = new ArrayList<>();
        for (LingBotCommand command : LingBotCommand.values()) {
            if (command.getScope() == LingBotCommand.Scope.PRIVATE) {
                privateCommands.add(command.toBotCommand());
            } else {
                groupCommands.add(command.toBotCommand());
            }
        }
        if (!privateCommands.isEmpty()) {
            registerCommands(BotCommandScopeAllPrivateChats.builder().build(), privateCommands);
        }
        if (!groupCommands.isEmpty()) {
            registerCommands(BotCommandScopeAllGroupChats.builder().build(), groupCommands);
        }
    }


    private void registerCommands(BotCommandScope scope, List<BotCommand> commands) throws TelegramApiException {
        telegramClient.execute(SetMyCommands.builder().scope(scope).commands(commands).build());
    }
}
