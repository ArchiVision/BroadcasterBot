package com.archivision.broadcaster.postgather;

import com.archivision.broadcaster.domain.SimplePostEvent;

import java.util.List;
import java.util.Optional;

public interface PostGather {
    Optional<List<SimplePostEvent>> getNewPosts();
}
