package com.archivision.broadcaster.messagesender;

import com.archivision.broadcaster.bot.BroadcasterBot;
import com.archivision.broadcaster.exception.bot.UnableSendMessageException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@Slf4j
@RequiredArgsConstructor
public class TgMessageSender implements MessageSender {
    @Lazy
    @Autowired
    private BroadcasterBot broadcasterBot;

    @Override
    public void sendMessage(String userId, String message) {
        log.info("Sending message={} to={}", message, userId);
        executeSendMessage(userId, message);
    }

    private void executeSendMessage(String userId, String message) {
        try {
            broadcasterBot.execute(SendMessage.builder()
                    .chatId(userId)
                    .text(message)
                    .build());
        } catch (TelegramApiException e) {
            throw new UnableSendMessageException("Unable to send a message", e);
        }
    }
}
