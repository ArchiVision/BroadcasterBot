package com.archivision.broadcaster.postgather;

import com.archivision.broadcaster.domain.SimplePostEvent;
import com.archivision.broadcaster.rss.RssPostsResolver;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Getter
public abstract class AbstractRssPostGather implements RssPostGather {
    private volatile List<SimplePostEvent> previousPosts;
    private final PostHandler postHandler;
    private final RssPostsResolver rssPostsResolver;

    public List<SimplePostEvent> callRss() {
        return rssPostsResolver.resolveBatchFromRssUrl(getRssUri());
    }

    @Override
    public Optional<List<SimplePostEvent>> getNewPosts() {
        log.info("Trying to get latest posts from: {}", getRssUri());

        final List<SimplePostEvent> simplePostEvents = callRss();
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
        return Optional.of(postHandler.returnDistinctPosts(previousPosts, simplePostEvents));
    }
}
