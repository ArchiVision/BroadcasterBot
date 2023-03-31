package com.pathz.broadcaster.postgather;

import com.pathz.broadcaster.domain.SimplePostEvent;
import com.pathz.broadcaster.rss.RssPostsResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class CnnPostGather implements PostGather {
    private volatile List<SimplePostEvent> previousPosts;
    private volatile RssPostsResolver rssPostsResolver;

    @Override
    public Optional<List<SimplePostEvent>> getNewPosts() {
        log.info("Trying to get latest posts from CNN...");

        final List<SimplePostEvent> simplePostEvents = callCnnRss();
        log.info("Resolved {} posts from cnn", simplePostEvents == null ? 0 : simplePostEvents.size());

        //add better comparison between post batches
        if (simplePostEvents != null && simplePostEvents.equals(previousPosts)) {
            log.info("This post has already sent to users. Return empty optional");
            return Optional.empty();
        }

        //return only new posts
        previousPosts = simplePostEvents;
        return Optional.of(simplePostEvents);
    }

    public List<SimplePostEvent> callCnnRss() {
        final String rssFeedUrl = "http://rss.cnn.com/rss/cnn_latest.rss";
        return rssPostsResolver.resolveBatchFromRssUrl(rssFeedUrl);
    }
}
