package com.archivision.broadcaster.util.command;

import java.util.Arrays;

public enum AdminCommands {

    DROP_USER("/drop_user"),
    SHUTDOWN_BOT("/sb");

    private final String cmd;

    AdminCommands(String cmd) {
        this.cmd = cmd;
    }

    public String getValue() {
        return cmd;
    }

    public static boolean checkInputIsAdminCmd(String input) {
        return Arrays.stream(AdminCommands.values())
                .map(AdminCommands::getValue)
                .anyMatch(input::contains);
    }
}
