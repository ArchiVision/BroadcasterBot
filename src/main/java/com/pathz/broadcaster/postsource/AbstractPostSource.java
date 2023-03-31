package com.pathz.broadcaster.postsource;

import com.pathz.broadcaster.domain.PostEvent;
import com.pathz.broadcaster.domain.SimplePostEvent;
import com.pathz.broadcaster.postgather.PostGather;
import com.pathz.broadcaster.queueprocessor.EventQueue;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@RequiredArgsConstructor
@NoArgsConstructor(force = true)
@Component
public abstract class AbstractPostSource implements PostSource {
    @Autowired
    private final EventQueue eventQueue;

    @Scheduled(fixedRate = 5000L, timeUnit = TimeUnit.MILLISECONDS)
    public void checkNewPost() {
        log.info("Checking for new post..");
        final Optional<List<SimplePostEvent>> newPosts = getPostGather().getNewPosts();

        if (newPosts.isPresent()) {
            for (SimplePostEvent simplePostEvent : newPosts.get()) {
                processEvent(simplePostEvent);
            }
        }
    }

    public void processEvent(PostEvent postEvent) {
        log.info("Got new event={}", postEvent);
        publishEvent(postEvent);
    }

    private void publishEvent(PostEvent postEvent) {
        log.info("Publishing event={} to a queue", postEvent);
        eventQueue.publishEvent(postEvent);
    }

    public abstract PostGather getPostGather();
}
