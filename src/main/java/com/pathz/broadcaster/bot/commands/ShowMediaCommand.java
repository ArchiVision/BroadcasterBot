package com.pathz.broadcaster.bot.commands;

import com.pathz.broadcaster.domain.CommunicationData;
import org.springframework.stereotype.Component;

@Component
public class ShowMediaCommand implements RequestCommand {
    @Override
    public CommunicationData perform(CommunicationData data) {
        return new CommunicationData("""
                Here is currently available media resources:
                    Medium
                """, data.telegramUserId());
    }

    @Override
    public String getCommandName() {
        return "/available_media";
    }
}
