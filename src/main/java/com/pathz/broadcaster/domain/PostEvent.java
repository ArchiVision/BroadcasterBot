package com.pathz.broadcaster.domain;

import java.util.List;

public interface PostEvent {
    String getUuid();

    List<String> getTopics();
    String getBody();
}
