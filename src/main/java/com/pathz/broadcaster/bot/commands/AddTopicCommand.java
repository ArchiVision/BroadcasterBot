package com.pathz.broadcaster.bot.commands;


import com.pathz.broadcaster.bot.commands.utils.TopicValidator;
import com.pathz.broadcaster.domain.CommunicationData;
import com.pathz.broadcaster.domain.entity.Topic;
import com.pathz.broadcaster.domain.entity.User;
import com.pathz.broadcaster.repo.TopicRepository;
import com.pathz.broadcaster.repo.UserRepository;
import com.pathz.broadcaster.service.user.UsersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class AddTopicCommand implements RequestCommand {
    private final UserRepository userRepository;
    private final TopicRepository topicRepository;
    private final UsersService usersService;
    private final TopicValidator topicValidator;

    @Override
    public CommunicationData perform(CommunicationData data) {
        final String[] inputTextArray = data.text().split(" ");

        if (inputTextArray.length != 2) {
            return new CommunicationData("Expected exactly one topic", data.telegramUserId());
        }

        final String topicName = inputTextArray[1];
        if (!topicValidator.isTopicValid(topicName)) {
            return new CommunicationData(topicValidator.getValidationMessage(), data.telegramUserId());
        }

        final User user = userRepository.findByTelegramId(data.telegramUserId());


        if (user == null) {
            final User newUser = new User();
            newUser.setTelegramUserId(data.telegramUserId());

            if (topicRepository.findAllTopicsByTopicName(topicName).isEmpty()) {
                userRepository.save(newUser);
                usersService.addTopicToUser(data.telegramUserId(), topicName);
                return new CommunicationData("Successfully created topic and new User", data.telegramUserId());
            } else {
                userRepository.save(newUser);
                usersService.addTopicToUser(data.telegramUserId(), topicName);
                return new CommunicationData("Successfully assigned topic to new User", data.telegramUserId());
            }
        } else {
            final List<Topic> allTopicsByUser = topicRepository.findAllTopicsByTopicNameAndUser(topicName, user);

            if (allTopicsByUser.isEmpty()) {
                usersService.addTopicToUser(data.telegramUserId(), topicName);
                return new CommunicationData("Successfully created topic for user", data.telegramUserId());
            } else {
                return new CommunicationData("This topic already exist", data.telegramUserId());
            }
        }
    }

    @Override
    public String getCommandName() {
        return "/add_topic";
    }
}
