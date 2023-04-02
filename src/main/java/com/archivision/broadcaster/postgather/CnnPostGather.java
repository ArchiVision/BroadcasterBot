package com.archivision.broadcaster.postgather;

import com.archivision.broadcaster.rss.RssPostsResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.archivision.broadcaster.rss.RssFeedUrl.CNN;

@Component
@Slf4j
public class CnnPostGather extends AbstractRssPostGather {
    public CnnPostGather(PostHandler postHandler, RssPostsResolver rssPostsResolver) {
        super(postHandler, rssPostsResolver);
    }

    @Override
    public String getRssUri() {
        return CNN.getUrl();
    }
}
