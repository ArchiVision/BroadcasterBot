package com.archivision.broadcaster.service.notifier;

import com.archivision.broadcaster.domain.PostEvent;
import com.archivision.broadcaster.service.user.UsersService;
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
        userService.notifyAboutNewPost(event);
    }
}
