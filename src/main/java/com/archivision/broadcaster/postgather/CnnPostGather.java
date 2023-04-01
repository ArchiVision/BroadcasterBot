package com.archivision.broadcaster.postgather;

import com.archivision.broadcaster.domain.SimplePostEvent;
import com.archivision.broadcaster.rss.RssPostsResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
@RequiredArgsConstructor
public class CnnPostGather extends AbstractRssPostGather {
    private volatile List<SimplePostEvent> previousPosts;
    private final RssPostsResolver rssPostsResolver;

    @Override
    public Optional<List<SimplePostEvent>> getNewPosts() {
        log.info("Trying to get latest posts from CNN...");

        final List<SimplePostEvent> simplePostEvents = callCnnRss();
        log.info("Resolved {} posts from cnn", simplePostEvents == null ? 0 : simplePostEvents.size());

        if (previousPosts == null && simplePostEvents != null) {
            previousPosts = simplePostEvents;
            return Optional.of(simplePostEvents);
        }

        if (simplePostEvents != null && simplePostEvents.equals(previousPosts)) {
            log.info("This post has already sent to users. Return empty optional");
            return Optional.empty();
        }

        previousPosts = simplePostEvents;
        return Optional.of(returnDistinctPosts(previousPosts, simplePostEvents));
    }

    public List<SimplePostEvent> returnDistinctPosts(List<SimplePostEvent> oldBatch, List<SimplePostEvent> newBatch) {
        if (newBatch == null || oldBatch == null) {
            return null;
        }

        List<SimplePostEvent> distinctPosts = new ArrayList<>();
        for (SimplePostEvent newPost : newBatch) {
            if (!oldBatch.contains(newPost)) {
                distinctPosts.add(newPost);
            }
        }

        return distinctPosts;
    }

    @Override
    public String getRssUri() {
        return "http://rss.cnn.com/rss/cnn_latest.rss";
    }

    @Override
    public RssPostsResolver getRssPostResolver() {
        return rssPostsResolver;
    }
}
