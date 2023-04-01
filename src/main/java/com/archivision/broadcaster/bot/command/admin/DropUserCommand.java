package com.archivision.broadcaster.bot.command.admin;

import com.archivision.broadcaster.bot.command.RequestCommand;
import com.archivision.broadcaster.domain.CommunicationData;
import com.archivision.broadcaster.service.user.UsersService;
import com.archivision.broadcaster.util.command.BotCmds;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DropUserCommand implements RequestCommand {

    private final UsersService usersService;

    @Override
    public CommunicationData perform(CommunicationData data) {
        Long telegramUserId = data.telegramUserId();
        final String[] inputTextArray = data.text().split(" ");

        if (inputTextArray.length != 2) {
            return new CommunicationData("Expected exactly one id", data.telegramUserId());
        }

        final Long userId = Long.parseLong(inputTextArray[1]);
        boolean isUserRemoved = usersService.removeUser(userId);
        String respText = "User with id=" + userId + " has not been remove from db";
        if (isUserRemoved) respText = respText.replace(" not", "");
        return new CommunicationData(respText, telegramUserId);
    }

    @Override
    public String getCommandName() {
        return BotCmds.DROP_USER;
    }
}
