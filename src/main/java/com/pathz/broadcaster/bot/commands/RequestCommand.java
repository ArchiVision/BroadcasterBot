package com.pathz.broadcaster.bot.commands;

import com.pathz.broadcaster.domain.CommunicationData;

public interface RequestCommand {
    CommunicationData perform(CommunicationData data);
    String getCommandName();
}
