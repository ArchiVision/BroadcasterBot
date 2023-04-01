package com.pathz.broadcaster.util.command;

import static com.pathz.broadcaster.util.command.BotCmds.*;

public interface CmdResponseTemplates {
    String START = """
            Hello. I am Broadcaster bot and I can help you with tracking media resources by topics.\s
            Here is list of commands which you can use:
                %s
                %s
                %s
                                    
                %s
            """.formatted(ADD_TOPIC,
            REMOVE_TOPIC,
            SHOW_TOPICS,
            AVAILABLE_MEDIA);

    String SHOW_TOPIC = "You have no added topics yet. Use '/add_topic TOPIC' command";
}
