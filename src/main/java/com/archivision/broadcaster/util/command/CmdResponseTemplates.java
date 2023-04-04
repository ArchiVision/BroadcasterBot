package com.archivision.broadcaster.util.command;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public interface CmdResponseTemplates {
    String START = """
            Hello. I am Broadcaster bot and I can help you with tracking media resources by topics.\s
            
            Here is list of commands which you can use:
            
            %s
            """.formatted(formatUserCommands());

    static String formatUserCommands() {
        return Arrays.stream(UserCommands.values())
                .filter(val -> val.getDesc() != null)
                .map(cmd -> cmd.getValue() + "\n" + cmd.getDesc())
                .collect(Collectors.joining("\n"));
    }

    String SHOW_TOPIC = "You have no added topics yet. Use '/add_topic TOPIC' command";
}
