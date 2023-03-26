package com.pathz.broadcaster.service.notifier;

import com.pathz.broadcaster.domain.PostEvent;
import com.pathz.broadcaster.service.user.UsersService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostUserNotifier implements UserNotifier {
    private final UsersService userService;

    @Override
    public void notifyUsers(PostEvent event) {
        log.info("Notifying all users about new event={}", event);
        userService.notifyAboutNewPost(event);
    }
}
