package com.archivision.broadcaster.bot.command;

import com.archivision.broadcaster.domain.CommunicationData;

public interface RequestCommand {
    CommunicationData perform(CommunicationData data);
    String getCommandName();
}
