package com.archivision.broadcaster.bot.command.util;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InputCommandMapper {
    private final RequestValidator requestValidator;

    public String mapRawTextToPureCommand(String inputText) {
        return inputText.split(" ")[0];
    }
}
