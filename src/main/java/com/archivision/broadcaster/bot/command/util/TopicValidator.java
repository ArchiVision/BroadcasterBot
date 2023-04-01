package com.archivision.broadcaster.bot.command.util;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
@Getter
public class TopicValidator {
    private String validationMessage;
    private final int MAXIMUM_TOPIC_SIZE = 20;
    public boolean isTopicValid(String topic) {
        if (topic.length() > MAXIMUM_TOPIC_SIZE) {
            validationMessage = "Topic name is too big";
            return false;
        }

        return true;
    }
}
