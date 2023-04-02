package com.archivision.broadcaster.postgather;

import com.archivision.broadcaster.domain.SimplePostEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PostHandler {
    public List<SimplePostEvent> returnDistinctPosts(List<SimplePostEvent> oldBatch, List<SimplePostEvent> newBatch) {
        if (newBatch == null || oldBatch == null) {
            return null;
        }

        List<SimplePostEvent> distinctPosts = new ArrayList<>();
        for (SimplePostEvent newPost : newBatch) {
            if (!oldBatch.contains(newPost)) {
                distinctPosts.add(newPost);
            }
        }

        return distinctPosts;
    }
}
