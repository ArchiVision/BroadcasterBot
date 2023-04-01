package com.archivision.broadcaster.bot.command.util;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Data
public class InputCommandMapper {
    private final RequestValidator requestValidator;

    public String mapRawTextToPureCommand(String inputText) {
        return inputText.split(" ")[0];
    }
}
