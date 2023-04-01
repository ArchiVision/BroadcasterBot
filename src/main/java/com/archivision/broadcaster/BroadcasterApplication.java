package com.archivision.broadcaster;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync
@RequiredArgsConstructor
public class BroadcasterApplication {
	public static void main(String[] args) {
		SpringApplication.run(BroadcasterApplication.class, args);
	}
}
