package com.pathz.broadcaster.bot;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
public class BotStarter {
    private final BotRegistrar botRegistrar;
    private final BroadcasterBot broadcasterBot;

    public void start() throws TelegramApiException {
        botRegistrar.register(broadcasterBot);
    }
}
