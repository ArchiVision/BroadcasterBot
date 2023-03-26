package com.pathz.broadcaster.bot.commands.utils;

import org.springframework.stereotype.Component;

@Component
public class RequestValidator {
    public boolean isCommand(String string) {
        return string != null && string.split("")[0].equals("/");
    }
}
