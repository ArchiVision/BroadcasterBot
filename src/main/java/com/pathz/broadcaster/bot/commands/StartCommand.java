package com.pathz.broadcaster.bot.commands;

import com.pathz.broadcaster.domain.CommunicationData;
import com.pathz.broadcaster.domain.entity.User;
import com.pathz.broadcaster.service.user.UsersService;
import com.pathz.broadcaster.util.command.BotCmds;
import com.pathz.broadcaster.util.command.CmdResponseTemplates;
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
        return BotCmds.START;
    }
}
