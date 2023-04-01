package com.pathz.broadcaster.repo;

import com.pathz.broadcaster.domain.entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    Set<Topic> findAllTopicsByName(String topicName);

    Topic findTopicByName(String topicName);
}
