package com.archivision.broadcaster.service.notifier;

import com.archivision.broadcaster.domain.PostEvent;

public interface UserNotifier {
    void notifyUsers(PostEvent event);
}
