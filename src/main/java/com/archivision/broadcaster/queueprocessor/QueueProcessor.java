package com.archivision.broadcaster.queueprocessor;

import com.archivision.broadcaster.domain.PostEvent;
import com.archivision.broadcaster.service.notifier.UserNotifier;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * The QueueProcessor class is an implementation of the EventQueue
 * interface that handles publishing and processing of PostEvents.
 * This class is used to provide a simple mechanism for handling and
 * processing PostEvents in a concurrent and thread-safe manner.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class QueueProcessor implements EventQueue {

    private final BlockingQueue<PostEvent> queue;
    private final UserNotifier userNotifier;

    @Scheduled(fixedRate = 4000L, timeUnit = TimeUnit.MILLISECONDS)
    @Override
    public void handleEvent() {
        final PostEvent newEvent = queue.poll();
        if (newEvent != null) {
            userNotifier.notifyUsers(newEvent);
        }
    }

    @Override
    public void publishEvent(PostEvent event) {
        log.info("Adding event={} to queue", event.getTrackingUuid());
        queue.add(event);
    }

}