package com.archivision.broadcaster.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
public class BotStarter {
    private final BotRegistrar botRegistrar;
    private final BroadcasterBot broadcasterBot;
    private final BotUpdateHandler botUpdateHandler;

    public void start() throws TelegramApiException {
        broadcasterBot.setUpdateHandler(botUpdateHandler);
        botRegistrar.register(broadcasterBot);
    }
}
