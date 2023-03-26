package com.pathz.broadcaster.bot;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException;
import org.telegram.telegrambots.meta.generics.LongPollingBot;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
public class BotRegistrar {
    private final Logger logger = LoggerFactory.getLogger(BotRegistrar.class);
    public void register(LongPollingBot telegramBot) throws TelegramApiException {
        final TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        try {
            logger.info("Registering bot...");
            telegramBotsApi.registerBot(telegramBot);
        } catch (TelegramApiRequestException e) {
            logger.error("Failed to register bot(check internet connection / bot token or make sure only one instance of bot is running). \n" + e);
        }

        logger.info("Telegram bot is ready to accept updates from user");
    }
}
