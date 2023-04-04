package com.archivision.broadcaster.bot.command.user;

import com.archivision.broadcaster.bot.command.RequestCommand;
import com.archivision.broadcaster.domain.CommunicationData;
import com.archivision.broadcaster.service.user.UsersService;
import com.archivision.broadcaster.util.command.UserCommands;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
@Slf4j
@RequiredArgsConstructor
public class DontSendAfterCommand implements RequestCommand {

    private final UsersService usersService;

    @Override
    public CommunicationData perform(CommunicationData data) {
        final String[] inputTextArray = data.text().split(" ");

        Long tgUserId = data.telegramUserId();

        if (inputTextArray.length != 2) {
            return new CommunicationData("You should provide time in 24h format: /dsa 22", tgUserId);
        }

        final String inputTime = inputTextArray[1];
        if (!isValid(inputTime)) {
            return new CommunicationData("Not correct format. Only digits in range from (0-23). -1 - disable this option at all", tgUserId);
        }

        int time = Integer.parseInt(inputTime);
        LocalTime dontSendAfter = time == -1 ? null : LocalTime.of(time, 0);
        usersService.setUserDontSendAfterValue(tgUserId, dontSendAfter);
        String msg = time != -1 ? "Now you will not receive new posts after %s".formatted(time) : "Now you will receive all posts.";
        return new CommunicationData(msg, tgUserId);
    }

    private boolean isValid(String strTime) {
        int time;
        try {
            time = Integer.parseInt(strTime);
        } catch (NumberFormatException e) {
            return false;
        }
        return time > -2 && time < 24;
    }

    @Override
    public String getCommandName() {
        return UserCommands.DONT_SEND_AFTER.getValue();
    }
}
