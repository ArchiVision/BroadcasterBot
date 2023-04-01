package com.archivision.broadcaster.util.command;

import java.util.List;
import java.util.Set;

public interface BotCmds {

    String START = "/start";

    /* ---------------------- USER COMMANDS ---------------------- */

    // topic related commands
    String ADD_TOPIC = "/add_topic";
    String REMOVE_TOPIC = "/rm_topic";
    String SHOW_TOPICS = "/show_topic";
    String AVAILABLE_MEDIA = "/available_media";

    /* ---------------------- ADMIN COMMANDS ---------------------- */
    String DROP_USER = "/drop_user";

    Set<String> ADMIN_COMMANDS = Set.of(DROP_USER);

    static boolean checkInputIsAdminCmd(String input) {
        return ADMIN_COMMANDS.stream().anyMatch(input::contains);
    }
}
