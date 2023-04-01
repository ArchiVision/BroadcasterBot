package com.archivision.broadcaster.service.topic;

import com.archivision.broadcaster.domain.entity.Topic;
import com.archivision.broadcaster.repo.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TopicService {
    @Autowired
    private TopicRepository topicRepository;

    public Topic findOrCreateTopic(String topicName) {
        Topic topic = topicRepository.findTopicByName(topicName);
        if (topic == null) {
            topic = new Topic();
            topic.setName(topicName);
            topicRepository.save(topic);
        }
        return topic;
    }
}
