package com.archivision.broadcaster.util.command;

public enum UserCommands {

    START("/start", null),
    ADD_TOPIC("/add_topic", """
            Adds topic. /add_topic topic_name
            """),

    REMOVE_TOPIC("/rm_topic", """
            Removes topic. /remove_topic topic_name
            """),

    SHOW_TOPICS("/topics", """
            Shows topics you're subscribed for
            """),

    AVAILABLE_MEDIA("/available_media", """
            Shows available resources we have to fetch new posts.
            """),

    PAUSE_POSTS("/p", """
            Pause receiving new posts.
            """),
    UNPAUSE_POSTS("/up", """
            Continue to receive new posts.
            """),

    DONT_SEND_AFTER("/dsa", """
            Don't send post after provided time.
            '/dsa 22' - Won't send new posts after 22:00
            Only digits in range from 0 to 23. 0 is 00:00.
            '/dsa -1' - Disable this option,
            """);

    private final String cmd;
    private final String desc;

    UserCommands(String cmd, String desc) {
        this.cmd = cmd;
        this.desc = desc;
    }

    public String getValue() {
        return cmd;
    }

    public String getDesc() {
        return desc;
    }
}
