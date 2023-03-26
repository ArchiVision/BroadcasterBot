package com.pathz.broadcaster.postsource;

import com.pathz.broadcaster.domain.PostEvent;
import com.pathz.broadcaster.postgather.MediumPostGather;
import com.pathz.broadcaster.queueprocessor.EventQueue;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
@Slf4j
public class MediumPostSource implements PostSource {
    private final MediumPostGather mediumPostGather;
    private final EventQueue eventQueue;

    @Scheduled(fixedRate = 1000L, timeUnit = TimeUnit.MILLISECONDS)
    public void checkNewPost() {
        log.info("Checking for new post..");
        mediumPostGather.getNewPost().ifPresent(this::processNewPostEvent);
    }

    private void processNewPostEvent(PostEvent postEvent) {
        log.info("Got new event={}", postEvent);
        publishEvent(postEvent);
    }

    private void publishEvent(PostEvent postEvent) {
        log.info("Publishing event={} to a queue", postEvent);
        eventQueue.publishEvent(postEvent);
    }
}
