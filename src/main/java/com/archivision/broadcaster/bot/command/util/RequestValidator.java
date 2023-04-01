package com.archivision.broadcaster.bot.command.util;

import org.springframework.stereotype.Component;

@Component
public class RequestValidator {
    public boolean isCommand(String string) {
        return string != null && string.split("")[0].equals("/");
    }
}
