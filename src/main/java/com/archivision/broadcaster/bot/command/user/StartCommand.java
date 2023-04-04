package com.archivision.broadcaster.bot.command.user;

import com.archivision.broadcaster.bot.command.RequestCommand;
import com.archivision.broadcaster.domain.CommunicationData;
import com.archivision.broadcaster.domain.entity.User;
import com.archivision.broadcaster.service.user.UsersService;
import com.archivision.broadcaster.util.command.CmdResponseTemplates;
import com.archivision.broadcaster.util.command.UserCommands;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class StartCommand implements RequestCommand {

    private final UsersService usersService;

    @Override
    @Transactional
    public CommunicationData perform(CommunicationData communicationData) {
        performRegistration(communicationData.telegramUserId());
        return new CommunicationData(CmdResponseTemplates.START, communicationData.telegramUserId());
    }

    private void performRegistration(Long telegramUserId) {
        User user = usersService.findByTelegramUserId(telegramUserId);
        if (user == null) {
            createUser(telegramUserId);
            return;
        }
        log.info("{} is already registered", user);
    }

    private void createUser(Long telegramUserId) {
        User user = new User();
        user.setTelegramUserId(telegramUserId);
        usersService.save(user);
    }

    @Override
    public String getCommandName() {
        return UserCommands.START.getValue();
    }
}
