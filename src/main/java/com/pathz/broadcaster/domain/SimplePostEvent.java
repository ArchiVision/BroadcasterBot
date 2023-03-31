package com.pathz.broadcaster.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.UUID;

@Data
@EqualsAndHashCode(exclude = "uuid")
public final class SimplePostEvent implements PostEvent {
    private final String uuid = UUID.randomUUID().toString();
    private final List<String> topics;
    private final String title;
    private final String description;
}
