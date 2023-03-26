package com.pathz.broadcaster.bot.commands;

import com.pathz.broadcaster.domain.CommunicationData;
import com.pathz.broadcaster.domain.entity.Topic;
import com.pathz.broadcaster.repo.TopicRepository;
import com.pathz.broadcaster.repo.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class ShowTopicsCommand implements RequestCommand {
    private final TopicRepository topicRepository;
    private final UserRepository userRepository;

    @Override
    public CommunicationData perform(CommunicationData data) {
        final String allTopicsInOneString = topicRepository
                .findAllTopicsByUser(userRepository.findByTelegramId(data.telegramUserId()))
                .stream()
                .map(Topic::getTopicName)
                .collect(Collectors.joining("\n"));

        return new CommunicationData(allTopicsInOneString, data.telegramUserId());
    }

    @Override
    public String getCommandName() {
        return "/show_topics";
    }
}
