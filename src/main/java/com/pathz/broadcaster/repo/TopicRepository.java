package com.pathz.broadcaster.repo;

import com.pathz.broadcaster.domain.entity.Topic;
import com.pathz.broadcaster.domain.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
    List<Topic> findAllTopicsByTopicName(String topicName);
    @Query("SELECT t FROM Topic t WHERE t.user = :user AND t.topicName = :topicName")
    List<Topic> findAllTopicsByTopicNameAndUser(@Param("topicName") String topicName, @Param("user") User user);
    List<Topic> findAllTopicsByUser(User user);
    Topic findTopicByTopicName(String topicName);

}
