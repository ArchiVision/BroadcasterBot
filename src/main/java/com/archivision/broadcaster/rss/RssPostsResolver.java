package com.archivision.broadcaster.rss;

import com.archivision.broadcaster.domain.SimplePostEvent;
import com.archivision.broadcaster.exception.rss.CannotRetrieveRssInfoException;
import com.archivision.broadcaster.service.topic.TopicResolver;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class RssPostsResolver {
    private final TopicResolver topicResolver;

    public List<SimplePostEvent> resolveBatchFromRssUrl(String endpoint) {
        List<SimplePostEvent> posts = new ArrayList<>();

        try {
            final URL url = new URL(endpoint);
            final SyndFeedInput input = new SyndFeedInput();
            final SyndFeed feed = input.build(new XmlReader(url));

            for (SyndEntry entry : feed.getEntries()) {
                final String trackingId = UUID.randomUUID().toString();

                final String title = entry.getTitle();
                final String description = entry.getDescription().getValue();

                final Set<String> topics = topicResolver.resolveTopicsFromPostInformation(title + " " + description);

                posts.add(new SimplePostEvent(trackingId, topics, title, description));
            }
        } catch (Exception e) {
            throw new CannotRetrieveRssInfoException("Unable to get rss news entries", e);
        }

        return posts;
    }
}
