package com.archivision.broadcaster.messagesender;

import com.archivision.broadcaster.domain.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class TgMessageSender implements MessageSender {
    @Override
    public void sendMessage(User user, String message) {
        log.info("Sending message={} to={}", message, user);
    }
}
