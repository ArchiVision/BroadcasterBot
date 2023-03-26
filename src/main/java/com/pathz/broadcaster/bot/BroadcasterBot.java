package com.pathz.broadcaster.bot;



import com.pathz.broadcaster.bot.commands.RequestCommand;
import com.pathz.broadcaster.bot.commands.utils.InputCommandMapper;
import com.pathz.broadcaster.bot.commands.utils.RequestValidator;
import com.pathz.broadcaster.domain.CommunicationData;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;


@Component
@RequiredArgsConstructor
public class BroadcasterBot extends TelegramLongPollingBot {
    @Value("${telegram.bot.username}")
    private String TELEGRAM_BOT_USERNAME;
    @Value("${telegram.bot.token}")
    private String TELEGRAM_BOT_TOKEN;

    private final RequestValidator requestValidator;
    private final List<RequestCommand> commandList;
    private final Logger logger = LoggerFactory.getLogger(BroadcasterBot.class);
    private final InputCommandMapper inputCommandMapper;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            final String textFromUser = update.getMessage().getText();

            final Long userId = update.getMessage().getChatId();
            final String userFirstName = update.getMessage().getFrom().getFirstName();

            logger.info("Received request: UserId: {}, userFirstName: {}, textFromUser: {}", userId, userFirstName, textFromUser);

            if (requestValidator.isCommand(update.getMessage().getText())) {
                for (RequestCommand requestCommand : commandList) {
                    if (requestCommand.getCommandName().equals(inputCommandMapper.mapRawTextToPureCommand(textFromUser))) {
                        try {
                            this.execute(SendMessage.builder()
                                        .chatId(userId.toString())
                                        .text(requestCommand.perform(new CommunicationData(textFromUser, userId)).text())
                                        .build());
                        } catch (TelegramApiException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return TELEGRAM_BOT_USERNAME;
    }

    @Override
    public String getBotToken() {
        return TELEGRAM_BOT_TOKEN;
    }
}
