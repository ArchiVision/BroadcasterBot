package com.pathz.broadcaster.service.notifier;

import com.pathz.broadcaster.domain.PostEvent;

public interface UserNotifier {
    void notifyUsers(PostEvent event);
}
