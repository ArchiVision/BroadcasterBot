package com.archivision.broadcaster.postgather;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.archivision.broadcaster.domain.SimplePostEvent;
import com.archivision.broadcaster.rss.RssPostsResolver;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

class CnnPostGatherTest {

    private CnnPostGather cnnPostGather;

    @Mock
    private RssPostsResolver rssPostsResolver;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        cnnPostGather = new CnnPostGather(rssPostsResolver);
    }

    @Test
    void testGetNewPosts_firstCall_returnsNewPosts() {
        // Arrange
        List<SimplePostEvent> posts = List.of(
                new SimplePostEvent("post1", Set.of(), "title1", "sfds1"),
                new SimplePostEvent("post2", Set.of(), "title2", "sfds2")
        );

        when(rssPostsResolver.resolveBatchFromRssUrl(anyString())).thenReturn(posts);

        // Act
        Optional<List<SimplePostEvent>> result = cnnPostGather.getNewPosts();

        // Assert
        assertEquals(posts, result.get());
    }

    @Test
    void testGetNewPosts_noNewPosts_returnsEmptyOptional() {
        // Arrange
        List<SimplePostEvent> posts = List.of(
                new SimplePostEvent("post1", Set.of(), "title1", "sfds1"),
                new SimplePostEvent("post2", Set.of(), "title2", "sfds2")
        );

        when(rssPostsResolver.resolveBatchFromRssUrl(anyString())).thenReturn(posts);

        // Act
        cnnPostGather.getNewPosts(); // populate previousPosts
        Optional<List<SimplePostEvent>> result = cnnPostGather.getNewPosts();

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetRssUri_returnsCorrectUri() {
        // Act
        String result = cnnPostGather.getRssUri();

        // Assert
        assertEquals("http://rss.cnn.com/rss/cnn_latest.rss", result);
    }

    @Test
    void testReturnDistinctPosts_returnsDistinctPosts() {
        // Arrange
        List<SimplePostEvent> oldPosts = List.of(
                new SimplePostEvent("post1", Set.of(), "title1", "sfds1"),
                new SimplePostEvent("post2", Set.of(), "title2", "sfds2")
        );

        List<SimplePostEvent> newPosts = List.of(
                new SimplePostEvent("post2", Set.of(), "title2", "sfds2"),
                new SimplePostEvent("post3", Set.of(), "title3", "sfds3")
        );

        // Act
        List<SimplePostEvent> result = cnnPostGather.returnDistinctPosts(oldPosts, newPosts);

        // Assert
        assertEquals(List.of(new SimplePostEvent("post3", Set.of(), "title3", "sfds3")), result);
    }
}