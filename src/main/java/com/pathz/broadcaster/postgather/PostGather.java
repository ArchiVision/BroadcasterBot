package com.pathz.broadcaster.postgather;

import com.pathz.broadcaster.domain.PostEvent;

import java.util.Optional;

public interface PostGather {
    Optional<PostEvent> getNewPost();
}
