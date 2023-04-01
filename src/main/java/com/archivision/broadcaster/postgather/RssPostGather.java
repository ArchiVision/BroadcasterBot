package com.archivision.broadcaster.postgather;

import com.archivision.broadcaster.rss.RssPostsResolver;

public interface RssPostGather extends PostGather {
    String getRssUri();

    RssPostsResolver getRssPostResolver();

}
