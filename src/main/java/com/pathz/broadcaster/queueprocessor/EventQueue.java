package com.pathz.broadcaster.queueprocessor;

import com.pathz.broadcaster.domain.PostEvent;

import java.util.concurrent.BlockingQueue;

public interface EventQueue {
    void publishEvent(PostEvent event);
    void handleEvent();
}
