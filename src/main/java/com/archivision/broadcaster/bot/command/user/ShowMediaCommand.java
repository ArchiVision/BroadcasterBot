package com.archivision.broadcaster.bot.command.user;

import com.archivision.broadcaster.bot.command.RequestCommand;
import com.archivision.broadcaster.domain.CommunicationData;
import com.archivision.broadcaster.postsource.AbstractPostSource;
import com.archivision.broadcaster.util.command.UserCommands;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ShowMediaCommand implements RequestCommand {
    private final List<AbstractPostSource> postSourceList;
    @Override
    public CommunicationData perform(CommunicationData data) {
        return new CommunicationData("""
                Here is currently available media resources:
                """ + postSourceList.stream().map(e -> e.getPostSourceName() + "\n").toList(), data.telegramUserId());
    }

    @Override
    public String getCommandName() {
        return UserCommands.AVAILABLE_MEDIA.getValue();
    }
}
