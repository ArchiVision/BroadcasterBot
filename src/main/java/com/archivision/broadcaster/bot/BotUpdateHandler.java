package com.archivision.broadcaster.bot;

import com.archivision.broadcaster.bot.command.RequestCommand;
import com.archivision.broadcaster.bot.command.util.InputCommandMapper;
import com.archivision.broadcaster.domain.CommunicationData;
import com.archivision.broadcaster.exception.bot.UnableSendMessageException;
import com.archivision.broadcaster.service.user.UsersService;
import com.archivision.broadcaster.util.command.BotCmds;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;
import java.util.function.Predicate;

@Component
@Slf4j
@RequiredArgsConstructor
public class BotUpdateHandler {

    private final List<RequestCommand> commandList;
    private final InputCommandMapper inputCommandMapper;
    private final UsersService usersService;

    @Lazy
    @Autowired
    private BroadcasterBot broadcasterBot;

    public void handle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            final String textFromUser = update.getMessage().getText();

            final Long userId = update.getMessage().getChatId();
            final String userFirstName = update.getMessage().getFrom().getFirstName();

            log.info("Received request: UserId: {}, userFirstName: {}, textFromUser: {}", userId, userFirstName, textFromUser);

            commandList.stream()
                    .filter(getAppropriateHandlerByUserInputCmd(textFromUser))
                    .findFirst()
                    .ifPresent(requestCommand -> {
                        if (isNotAdminUserUsesAdminCmd(textFromUser, userId)) {
                            return;
                        }
                        executeCommand(textFromUser, userId, requestCommand);
                    });
        }
    }

    private void executeCommand(String textFromUser, Long userId, RequestCommand requestCommand) {
        try {
            CommunicationData communicationData = new CommunicationData(textFromUser, userId);
            broadcasterBot.execute(SendMessage.builder()
                    .chatId(userId.toString())
                    .text(requestCommand.perform(communicationData).text())
                    .build());
        } catch (TelegramApiException e) {
            throw new UnableSendMessageException("Cannot send a message", e);
        }
    }

    private boolean isNotAdminUserUsesAdminCmd(String textFromUser, Long userId) {
        return !usersService.isAdmin(userId) && BotCmds.checkInputIsAdminCmd(textFromUser);
    }

    private Predicate<RequestCommand> getAppropriateHandlerByUserInputCmd(String textFromUser) {
        return requestCommand -> requestCommand.getCommandName().equals(inputCommandMapper.mapRawTextToPureCommand(textFromUser));
    }
}
