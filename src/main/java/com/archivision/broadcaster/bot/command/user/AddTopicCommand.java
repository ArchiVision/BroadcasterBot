package com.archivision.broadcaster.bot.command.user;


import com.archivision.broadcaster.bot.command.RequestCommand;
import com.archivision.broadcaster.bot.command.util.TopicValidator;
import com.archivision.broadcaster.domain.CommunicationData;
import com.archivision.broadcaster.domain.entity.Topic;
import com.archivision.broadcaster.domain.entity.User;
import com.archivision.broadcaster.repo.UserRepository;
import com.archivision.broadcaster.service.user.UsersService;
import com.archivision.broadcaster.util.command.BotCmds;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class AddTopicCommand implements RequestCommand {
    private final UserRepository userRepository;
    private final TopicValidator topicValidator;
    private final UsersService usersService;

    @Override
    @Transactional
    public CommunicationData perform(CommunicationData data) {
        final String[] inputTextArray = data.text().split(" ");

        if (inputTextArray.length != 2) {
            return new CommunicationData("Expected exactly one topic", data.telegramUserId());
        }

        final String topicName = inputTextArray[1];
        if (!topicValidator.isTopicValid(topicName)) {
            return new CommunicationData(topicValidator.getValidationMessage(), data.telegramUserId());
        }

        // Since 'add_topic' command is used after /start, we expect user is already created
        final User user = userRepository.findByTelegramUserId(data.telegramUserId());
        final Set<Topic> userTopics = user.getTopics();
        boolean isTopicAlreadyExist = userTopics.stream()
                .map(Topic::getName)
                .anyMatch(name -> name.equals(topicName));

        if (isTopicAlreadyExist) {
            return new CommunicationData("This topic already exist", data.telegramUserId());
        }
        usersService.addUserTopic(data.telegramUserId(), topicName);
        return new CommunicationData("Successfully created topic for user", data.telegramUserId());
    }

    @Override
    public String getCommandName() {
        return BotCmds.ADD_TOPIC;
    }
}
