package space.changle.lingbot.bot.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.client.okhttp.OkHttpTelegramClient;
import org.telegram.telegrambots.longpolling.TelegramBotsLongPollingApplication;
import org.telegram.telegrambots.longpolling.util.TelegramOkHttpClientFactory;
import org.telegram.telegrambots.meta.generics.TelegramClient;

import java.net.InetSocketAddress;
import java.net.Proxy;

/**
 * @author 长乐
 * @version 1.0.0
 * @date 2026/5/8
 * @time 07:52
 * @description Telegram配置类
 */
@Configuration
public class TelegramConfig {


    @Bean(value = "okHttpClient")
    public OkHttpClient okHttpClient(@Value("${telegram.proxy.host}")String host,@Value("${telegram.proxy.port}")int port){
        return new TelegramOkHttpClientFactory.SocksProxyOkHttpClientCreator(() -> new Proxy(Proxy.Type.SOCKS, new InetSocketAddress(host, port))).get();
    }


    @Bean(value = "telegramClient")
    public TelegramClient telegramClient(@Value("${telegram.bot.token}")String botToken, @Qualifier("okHttpClient") OkHttpClient okHttpClient){
      return new OkHttpTelegramClient(okHttpClient, botToken);
    }

    @Bean
    public TelegramBotsLongPollingApplication telegramBotsLongPollingApplication(@Qualifier("okHttpClient") OkHttpClient okHttpClient){
        return new TelegramBotsLongPollingApplication(ObjectMapper::new, () -> okHttpClient);
    }
}
