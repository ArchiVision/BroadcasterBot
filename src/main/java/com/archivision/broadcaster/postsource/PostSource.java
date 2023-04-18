package com.archivision.broadcaster.postsource;

public enum PostSource {
    CNN("CNN"),
    WIRED("Wire"),
    ZD_NET("ZD Net");

    private final String sourceName;

    PostSource(String sourceName) {
        this.sourceName = sourceName;
    }

    public String getSourceName() {
        return sourceName;
    }
}
