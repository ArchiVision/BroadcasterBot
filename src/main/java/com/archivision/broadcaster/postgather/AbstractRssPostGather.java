package com.archivision.broadcaster.postgather;

import com.archivision.broadcaster.domain.SimplePostEvent;

import java.util.List;

public abstract class AbstractRssPostGather implements RssPostGather {
    public List<SimplePostEvent> callCnnRss() {
        return getRssPostResolver().resolveBatchFromRssUrl(getRssUri());
    }
}
