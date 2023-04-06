package com.archivision.broadcaster.rss;

public enum RssFeedUrl {
    CNN("http://rss.cnn.com/rss/cnn_latest.rss"),
    WIRED("https://www.wired.com/feed/rss"),
    ZDNET("https://www.zdnet.com/news/rss.xml");

    private final String url;

    RssFeedUrl(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
