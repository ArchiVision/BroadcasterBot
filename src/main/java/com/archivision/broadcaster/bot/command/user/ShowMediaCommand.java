package com.archivision.broadcaster.bot.command.user;

import com.archivision.broadcaster.bot.command.RequestCommand;
import com.archivision.broadcaster.domain.CommunicationData;
import com.archivision.broadcaster.util.command.UserCommands;
import org.springframework.stereotype.Component;

@Component
public class ShowMediaCommand implements RequestCommand {
    @Override
    public CommunicationData perform(CommunicationData data) {
        return new CommunicationData("""
                Here is currently available media resources:
                    The Verge
                    CNN
                    Wired
                """, data.telegramUserId());
    }

    @Override
    public String getCommandName() {
        return UserCommands.AVAILABLE_MEDIA.getValue();
    }
}
