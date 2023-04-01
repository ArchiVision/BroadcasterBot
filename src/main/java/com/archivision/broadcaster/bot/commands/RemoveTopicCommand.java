package com.archivision.broadcaster.bot.commands;

import com.archivision.broadcaster.domain.CommunicationData;
import com.archivision.broadcaster.domain.entity.User;
import com.archivision.broadcaster.service.user.UsersService;
import com.archivision.broadcaster.util.command.BotCmds;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RemoveTopicCommand implements RequestCommand {
    private final UsersService usersService;

    @Override
    @Transactional
    public CommunicationData perform(CommunicationData data) {
        final String[] inputTextArray = data.text().split(" ");

        if (inputTextArray.length != 2) {
            return new CommunicationData("Expected exactly one topic", data.telegramUserId());
        }

        final String topicName = inputTextArray[1];
        final User user = usersService.findByTelegramUserId(data.telegramUserId());

        if (user == null) {
            return new CommunicationData("Sorry, we don't have you in DB, please add new topic", data.telegramUserId());
        } else if (user.getTopics().isEmpty()) {
            return new CommunicationData("Sorry, you don't have any topics", data.telegramUserId());
        }

        usersService.removeTopicFromUser(data.telegramUserId(), topicName);

        return new CommunicationData("Successfully removed topic", data.telegramUserId());
    }

    @Override
    public String getCommandName() {
        return BotCmds.REMOVE_TOPIC;
    }
}
