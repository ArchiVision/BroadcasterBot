package com.archivision.broadcaster.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Data
@EqualsAndHashCode(exclude = "trackingUuid")
public final class SimplePostEvent implements PostEvent {
    private final String trackingUuid;
    private final Set<String> topics;
    private final String title;
    private final String description;
    private final String mediaOriginName;
    private final String linkToOriginal;
    private final String postTime;

    @Override
    public String getBodyText() {
        return mediaOriginName + "\n\n" + title + "\n\n" + description + "\n\n" + postTime + "\n\n" + linkToOriginal;
    }
}
