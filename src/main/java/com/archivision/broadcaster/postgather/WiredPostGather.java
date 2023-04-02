package com.archivision.broadcaster.postgather;

import com.archivision.broadcaster.rss.RssPostsResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.archivision.broadcaster.rss.RssFeedUrl.WIRED;

@Component
@Slf4j
public class WiredPostGather extends AbstractRssPostGather {
    public WiredPostGather(PostHandler postHandler, RssPostsResolver rssPostsResolver) {
        super(postHandler, rssPostsResolver);
    }

    @Override
    public String getRssUri() {
        return WIRED.getUrl();
    }
}
