package com.archivision.broadcaster.service.user;

import com.archivision.broadcaster.domain.PostEvent;
import com.archivision.broadcaster.domain.entity.Topic;
import com.archivision.broadcaster.domain.entity.User;
import com.archivision.broadcaster.messagesender.MessageSender;
import com.archivision.broadcaster.repo.UserRepository;
import com.archivision.broadcaster.service.topic.TopicService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;

@Service
@RequiredArgsConstructor
@Slf4j
public class UsersService {
    private final UserRepository userRepository;
    private final TopicService topicService;
    private final MessageSender messageSender;
    private final ExecutorService usersNotifierExecutorService;

    @Value(value = "${admins.telegram.id}")
    private Set<Long> adminIdentifiers = new HashSet<>();

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public User findByTelegramUserId(Long userId) {
        return userRepository.findByTelegramUserId(userId);
    }

    @Transactional
    public void notifyAboutNewPost(PostEvent event) {
        final Set<User> allUsersByTopic = userRepository.findUsersByTopicNames(event.getTopics());
        if (!allUsersByTopic.isEmpty()) {
            log.info("Notifying users: {}, by topics : {}", allUsersByTopic, event.getTopics());
            usersNotifierExecutorService.submit(() -> {
                for (User user : allUsersByTopic) {
                    messageSender.sendMessage(user.getTelegramUserId().toString(), event.getBodyText());
                }
            });
        }
    }

    @Transactional
    public boolean addUserTopic(Long telegramId, String topicName) {
        User user = userRepository.findByTelegramUserId(telegramId);
        if (user == null) {
            return false;
        }
        Topic topic = topicService.findOrCreateTopic(topicName);
        user.addTopic(topic);
        return true;
    }

    public boolean isAdmin(Long tgId) {
        return adminIdentifiers.contains(tgId);
    }

    public boolean removeUser(Long userId) {
        User user = userRepository.findByTelegramUserId(userId);
        if (user != null) {
            userRepository.delete(user);
            return true;
        }
        return false;
    }
}

