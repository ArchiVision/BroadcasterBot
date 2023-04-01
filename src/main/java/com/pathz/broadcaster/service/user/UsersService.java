package com.pathz.broadcaster.service.user;

import com.pathz.broadcaster.domain.PostEvent;
import com.pathz.broadcaster.domain.entity.Topic;
import com.pathz.broadcaster.domain.entity.User;
import com.pathz.broadcaster.exception.user.UserNotFoundException;
import com.pathz.broadcaster.messagesender.MessageSender;
import com.pathz.broadcaster.repo.TopicRepository;
import com.pathz.broadcaster.repo.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersService {
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;

    private final MessageSender messageSender;
    private final ExecutorService usersNotifierExecutorService;

    @Value("${thread.pool.size}")
    private int notifierThreadPoolSize;

    @Transactional
    public void addTopicToUser(Long userId, String topicName) {
        final User user = userRepository.findByTelegramUserId(userId);
        if (user == null) {
            throw new UserNotFoundException(userId);
        }

        Topic topic = topicRepository.findTopicByName(topicName);
        if (topic == null) {
            topic = new Topic();
            topic.setName(topicName);
        }

        user.addTopic(topic);
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void removeTopicFromUser(Long userId, String topicName) {
        final Optional<User> optionalUser = Optional.ofNullable(userRepository.findByTelegramUserId(userId));
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        User user = optionalUser.get();
        user.removeTopic(topicRepository.findTopicByName(topicName));

        userRepository.save(user);
    }

    @Transactional
    public User findByTelegramUserId(Long userId) {
        return userRepository.findByTelegramUserId(userId);
    }

    @Async
    @Transactional
    public void notifyAboutNewPost(PostEvent event) {
        final List<User> allUsersByTopic = userRepository.findUsersByTopicNames(event.getTopics());
        log.info("Notifying users: {}, by topics : {}", allUsersByTopic, event.getTopics());
        for (User user : allUsersByTopic) {
            sendMessage(event, user);
        }
    }

    private void sendMessage(PostEvent event, User user) {
        messageSender.sendMessage(user, event.getTitle());
    }
}

