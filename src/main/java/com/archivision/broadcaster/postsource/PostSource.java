package com.archivision.broadcaster.postsource;

import com.archivision.broadcaster.domain.PostEvent;
import com.archivision.broadcaster.postgather.PostGather;

public interface PostSource {
    void checkNewPost();
    PostGather getPostGather();
    void processEvent(PostEvent postEvent);
}
