package com.pathz.broadcaster.postgather;

import com.pathz.broadcaster.domain.PostEvent;
import com.pathz.broadcaster.domain.SimplePostEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * A class that implements the {@link PostGather} interface and retrieves new posts from Medium.
 * This class uses a hashcode comparison to ensure that only new posts are returned.
 */
@Component
@Slf4j
public class MediumPostGather implements PostGather {

    private volatile PostEvent prevPost;

    @Override
    public Optional<PostEvent> getNewPost() {
        log.info("About getting new post from medium..");
        PostEvent postEvent = callMediumApi();
        log.info("Post from Medium={}", postEvent);
        if (postEvent.equals(prevPost)) {
            log.info("This post has already sent to users. Return empty optional");
            return Optional.empty();
        }
        prevPost = postEvent;
        return Optional.of(postEvent);
    }

    public PostEvent callMediumApi() {
        log.info("Calling Medium API to get a post [!fake!]");
        String body = "This is post about java";
        List<String> topics = List.of("java");
        return new SimplePostEvent(topics, body);
    }
}
