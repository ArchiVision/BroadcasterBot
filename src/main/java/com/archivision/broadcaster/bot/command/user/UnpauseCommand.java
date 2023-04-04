package com.archivision.broadcaster.bot.command.user;

import com.archivision.broadcaster.bot.command.RequestCommand;
import com.archivision.broadcaster.domain.CommunicationData;
import com.archivision.broadcaster.service.user.UsersService;
import com.archivision.broadcaster.util.command.UserCommands;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class UnpauseCommand implements RequestCommand {

    private final UsersService usersService;

    @Override
    public CommunicationData perform(CommunicationData data) {
        Long tgUserId = data.telegramUserId();
        usersService.pauseReceivingPostForUser(tgUserId, false);
        return new CommunicationData("The receiving of posts has been continued!", tgUserId);
    }

    @Override
    public String getCommandName() {
        return UserCommands.UNPAUSE_POSTS.getValue();
    }
}
