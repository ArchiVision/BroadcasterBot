package com.archivision.broadcaster.bot.commands;

import com.archivision.broadcaster.domain.CommunicationData;

public interface RequestCommand {
    CommunicationData perform(CommunicationData data);
    String getCommandName();
}
