package com.pathz.broadcaster.domain;

import java.util.List;

public interface PostEvent {
    String getTrackingUuid();

    List<String> getTopics();
    String getTitle();
}
