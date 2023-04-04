package com.archivision.broadcaster.bot.command.admin;

import com.archivision.broadcaster.bot.command.RequestCommand;
import com.archivision.broadcaster.domain.CommunicationData;
import com.archivision.broadcaster.util.command.AdminCommands;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ShutdownBotCommand implements RequestCommand {
    @Override
    public CommunicationData perform(CommunicationData data) {
        log.info("Admin send SHUTDOWN COMMAND. About to exit..");
        Runtime.getRuntime().exit(0);
        return new CommunicationData("SHUTDOWN COMPLETE", data.telegramUserId());
    }

    @Override
    public String getCommandName() {
        return AdminCommands.SHUTDOWN_BOT.getValue();
    }
}
