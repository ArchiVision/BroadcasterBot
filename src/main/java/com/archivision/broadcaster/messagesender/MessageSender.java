package com.archivision.broadcaster.messagesender;

import com.archivision.broadcaster.domain.entity.User;

public interface MessageSender {
    void sendMessage(User user, String message);
}
