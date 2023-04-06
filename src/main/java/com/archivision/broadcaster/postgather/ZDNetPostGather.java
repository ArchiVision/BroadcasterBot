package com.archivision.broadcaster.postgather;

import com.archivision.broadcaster.rss.RssPostsResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import static com.archivision.broadcaster.rss.RssFeedUrl.ZDNET;

@Component
@Slf4j
public class ZDNetPostGather extends AbstractRssPostGather {
    public ZDNetPostGather(PostHandler postHandler, RssPostsResolver rssPostsResolver) {
        super(postHandler, rssPostsResolver);
    }

    @Override
    public String getRssUri() {
        return ZDNET.getUrl();
    }
}
