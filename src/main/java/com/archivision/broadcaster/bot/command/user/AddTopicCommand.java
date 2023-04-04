package com.archivision.broadcaster.bot.command.user;


import com.archivision.broadcaster.bot.command.RequestCommand;
import com.archivision.broadcaster.bot.command.util.TopicValidator;
import com.archivision.broadcaster.domain.CommunicationData;
import com.archivision.broadcaster.service.user.UsersService;
import com.archivision.broadcaster.util.command.UserCommands;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AddTopicCommand implements RequestCommand {
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

        boolean isTopicCreated = usersService.addUserTopic(data.telegramUserId(), topicName);
        if (isTopicCreated) {
            return new CommunicationData("Successfully created topic for user", data.telegramUserId());
        }
        return new CommunicationData("This topic already exist", data.telegramUserId());
    }

    @Override
    public String getCommandName() {
        return UserCommands.ADD_TOPIC.getValue();
    }
}
