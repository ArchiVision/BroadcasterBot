package com.pathz.broadcaster.service.user;

import com.pathz.broadcaster.domain.PostEvent;
import com.pathz.broadcaster.domain.entity.Topic;
import com.pathz.broadcaster.domain.entity.User;
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
import java.util.stream.IntStream;

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
        final Optional<User> optionalUser = Optional.ofNullable(userRepository.findByTelegramId(userId));
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        final User user = optionalUser.get();

        final Topic topic = new Topic();
        topic.setTopicName(topicName);
        topic.setUser(user);

        user.addTopic(topic);

        userRepository.save(user);
    }

    @Transactional
    public void removeTopicFromUser(Long userId, String topicName) {
        final Optional<User> optionalUser = Optional.ofNullable(userRepository.findByTelegramId(userId));
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException("User not found");
        }

        User user = optionalUser.get();
        user.removeTopic(topicRepository.findTopicByTopicName(topicName));

        userRepository.save(user);
    }

    public List<User> getAllUsersByOneOfTopics(List<String> topic) {
        return List.of(new User(), new User());
    }

    @Async
    public void notifyAboutNewPost(PostEvent event) {
        List<User> allUsersByTopic = getAllUsersByOneOfTopics(event.getTopics());
        log.info("Users that related to {} topic={}", event.getTopics(), allUsersByTopic);
        executePartitioningNotifying(allUsersByTopic, event);
    }

    private void executePartitioningNotifying(List<User> allUsersByTopic, PostEvent event) {
        int chunkSize = (int) Math.ceil((double) allUsersByTopic.size() / notifierThreadPoolSize);

        List<List<User>> subLists = IntStream.range(0, notifierThreadPoolSize)
                .mapToObj(i -> allUsersByTopic.subList(i * chunkSize, Math.min((i + 1) * chunkSize, allUsersByTopic.size())))
                .toList();

        for (List<User> subList : subLists) {
            usersNotifierExecutorService.submit(() -> subList.forEach(user -> sendMessage(event, user)));
        }
    }

    private void sendMessage(PostEvent event, User user) {
        messageSender.sendMessage(user, event.getBody());
    }
}

