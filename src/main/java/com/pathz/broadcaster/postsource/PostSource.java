package com.pathz.broadcaster.postsource;

import com.pathz.broadcaster.domain.PostEvent;
import com.pathz.broadcaster.postgather.PostGather;

public interface PostSource {
    void checkNewPost();
    PostGather getPostGather();
    void processEvent(PostEvent postEvent);
}
