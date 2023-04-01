package com.pathz.broadcaster.bot.commands;

import com.pathz.broadcaster.domain.CommunicationData;
import com.pathz.broadcaster.domain.entity.Topic;
import com.pathz.broadcaster.domain.entity.User;
import com.pathz.broadcaster.repo.TopicRepository;
import com.pathz.broadcaster.repo.UserRepository;
import com.pathz.broadcaster.util.command.BotCmds;
import com.pathz.broadcaster.util.command.CmdResponseTemplates;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

@Component
@RequiredArgsConstructor
public class ShowTopicsCommand implements RequestCommand {
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CommunicationData perform(CommunicationData data) {
        User user = userRepository.findByTelegramUserId(data.telegramUserId());
        Set<Topic> userTopics = user.getTopics();
        if (userTopics.isEmpty()) {
            return new CommunicationData(CmdResponseTemplates.SHOW_TOPIC, data.telegramUserId());
        }

        return new CommunicationData(generateTopics(userTopics), data.telegramUserId());
    }

    private String generateTopics(Set<Topic> userTopics) {
        StringBuilder sb = new StringBuilder("Your topics:\n");
        List<Topic> topicList = userTopics.stream().toList();

        IntStream.range(0, topicList.size())
                .mapToObj(i -> i + 1 + ". " + topicList.get(i).getName() + '\n')
                .forEach(sb::append);

        return sb.toString();
    }

    @Override
    public String getCommandName() {
        return BotCmds.SHOW_TOPICS;
    }
}
