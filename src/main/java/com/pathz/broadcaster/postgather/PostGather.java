package com.pathz.broadcaster.postgather;

import com.pathz.broadcaster.domain.SimplePostEvent;

import java.util.List;
import java.util.Optional;

public interface PostGather {
    Optional<List<SimplePostEvent>> getNewPosts();
}
