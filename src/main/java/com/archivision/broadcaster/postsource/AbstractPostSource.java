package com.archivision.broadcaster.postsource;

import com.archivision.broadcaster.domain.PostEvent;
import com.archivision.broadcaster.domain.SimplePostEvent;
import com.archivision.broadcaster.postgather.PostGather;
import com.archivision.broadcaster.queueprocessor.EventQueue;
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

    @Scheduled(fixedRate = 4000L, timeUnit = TimeUnit.MILLISECONDS)
    public void checkNewPost() {
        log.info("Checking for new post..");
        final Optional<List<SimplePostEvent>> newPosts = getPostGather().getNewPosts();

        if (newPosts.isPresent() && newPosts.get().size() > 0) {
            for (SimplePostEvent simplePostEvent : newPosts.get()) {
                processEvent(simplePostEvent);
            }
        }
    }

    public void processEvent(PostEvent postEvent) {
        publishEvent(postEvent);
    }

    private void publishEvent(PostEvent postEvent) {
        log.info("Publishing event : {}", postEvent.getTrackingUuid());
        eventQueue.publishEvent(postEvent);
    }

    public abstract PostGather getPostGather();
}
