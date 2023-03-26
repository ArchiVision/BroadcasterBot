package com.pathz.broadcaster.bot.commands;

import com.pathz.broadcaster.domain.CommunicationData;
import org.springframework.stereotype.Component;

@Component
public class StartCommand implements RequestCommand {
    @Override
    public CommunicationData perform(CommunicationData communicationData) {
        final String initialText = """
                Hello. I am Broadcaster bot and I can help you with tracking media resources by topics. 
                Here is list of commands which you can use:
                    /add_topic
                    /rm_topic
                    /show_topics
                    
                    /available_media
                """;

        return new CommunicationData(initialText, communicationData.telegramUserId());
    }

    @Override
    public String getCommandName() {
        return "/start";
    }
}
