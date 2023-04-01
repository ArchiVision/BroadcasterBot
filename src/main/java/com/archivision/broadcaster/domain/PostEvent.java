package com.archivision.broadcaster.domain;

import java.util.Set;

public interface PostEvent {
    String getTrackingUuid();
    Set<String> getTopics();
    String getTitle();
}
