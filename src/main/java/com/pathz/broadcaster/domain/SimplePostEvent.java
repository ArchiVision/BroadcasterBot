package com.pathz.broadcaster.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@Data
@EqualsAndHashCode(exclude = "trackingUuid")
public final class SimplePostEvent implements PostEvent {
    private final String trackingUuid;
    private final List<String> topics;
    private final String title;
    private final String description;
}
