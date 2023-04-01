package com.pathz.broadcaster.bot.commands;

import com.pathz.broadcaster.domain.CommunicationData;
import com.pathz.broadcaster.domain.entity.User;
import com.pathz.broadcaster.service.user.UsersService;
import com.pathz.broadcaster.util.command.BotCmds;
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
