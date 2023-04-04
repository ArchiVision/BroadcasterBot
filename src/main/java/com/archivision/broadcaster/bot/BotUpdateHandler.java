package com.archivision.broadcaster.bot;

import com.archivision.broadcaster.bot.command.RequestCommand;
import com.archivision.broadcaster.bot.command.util.InputCommandMapper;
import com.archivision.broadcaster.domain.CommunicationData;
import com.archivision.broadcaster.messagesender.MessageSender;
import com.archivision.broadcaster.service.user.UsersService;
import com.archivision.broadcaster.util.command.AdminCommands;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.function.Predicate;

@Component
@Slf4j
@RequiredArgsConstructor
public class BotUpdateHandler {
    private final List<RequestCommand> commandList;
    private final InputCommandMapper inputCommandMapper;
    private final UsersService usersService;
    private final MessageSender messageSender;

    public void handle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            final String textFromUser = update.getMessage().getText();

            final Long userId = update.getMessage().getChatId();
            final String userFirstName = update.getMessage().getFrom().getFirstName();

            log.info("Received request: UserId: {}, userFirstName: {}, textFromUser: {}", userId, userFirstName, textFromUser);

            if (inputCommandMapper.getRequestValidator().isCommand(textFromUser)) {
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
    }

    private void executeCommand(String textFromUser, Long userId, RequestCommand requestCommand) {
        final CommunicationData communicationData = new CommunicationData(textFromUser, userId);
        final String message = requestCommand.perform(communicationData).text();
        messageSender.sendMessage(String.valueOf(userId), message);
    }

    private boolean isNotAdminUserUsesAdminCmd(String textFromUser, Long userId) {
        return !usersService.isAdmin(userId) && AdminCommands.checkInputIsAdminCmd(textFromUser);
    }

    private Predicate<RequestCommand> getAppropriateHandlerByUserInputCmd(String textFromUser) {
        return requestCommand -> requestCommand.getCommandName().equals(inputCommandMapper.mapRawTextToPureCommand(textFromUser));
    }
}
