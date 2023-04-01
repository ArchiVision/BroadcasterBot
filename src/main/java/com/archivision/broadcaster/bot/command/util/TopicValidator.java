package com.archivision.broadcaster.bot.command.util;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class TopicValidator {
    private String validationMessage;
    public boolean isTopicValid(String topic) {
        if (topic.length() > 10) {
            validationMessage = "Topic name is too big";
            return false;
        }

        return true;
    }
}
