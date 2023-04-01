package com.archivision.broadcaster.queueprocessor;

import com.archivision.broadcaster.domain.PostEvent;

public interface EventQueue {
    void publishEvent(PostEvent event);
    void handleEvent();
}
