package com.pathz.broadcaster;

import com.pathz.broadcaster.bot.BotStarter;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@RequiredArgsConstructor
public class BroadcasterApplication {
	private final BotStarter botStarter;

	@PostConstruct
	public void init() throws TelegramApiException {
		botStarter.start();
	}

	public static void main(String[] args) {
		SpringApplication.run(BroadcasterApplication.class, args);
	}
}
