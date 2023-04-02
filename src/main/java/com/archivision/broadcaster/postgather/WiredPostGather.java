package com.archivision.broadcaster.postgather;

import com.archivision.broadcaster.domain.SimplePostEvent;
import com.archivision.broadcaster.rss.RssPostsResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static com.archivision.broadcaster.rss.RssFeedUrl.WIRED;

@Component
@Slf4j
@RequiredArgsConstructor
public class WiredPostGather extends AbstractRssPostGather {
    private volatile List<SimplePostEvent> previousPosts;
    private final RssPostsResolver rssPostsResolver;
    private final PostHandler postHandler;

    @Override
    public Optional<List<SimplePostEvent>> getNewPosts() {
        log.info("Trying to get latest posts from Wired...");

        final List<SimplePostEvent> simplePostEvents = callRss();
        log.info("Resolved {} posts from wired", simplePostEvents == null ? 0 : simplePostEvents.size());

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

    @Override
    public String getRssUri() {
        return WIRED.getUrl();
    }

    @Override
    public RssPostsResolver getRssPostResolver() {
        return rssPostsResolver;
    }
}
