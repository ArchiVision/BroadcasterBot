package com.pathz.broadcaster.messagesender;

import com.pathz.broadcaster.domain.entity.User;

public interface MessageSender {
    void sendMessage(User user, String message);
}
