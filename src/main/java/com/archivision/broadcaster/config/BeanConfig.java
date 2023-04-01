package com.archivision.broadcaster.config;

import com.archivision.broadcaster.domain.PostEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;

import java.util.concurrent.*;

@Configuration
@PropertySource("classpath:application.properties")
public class BeanConfig {

    @Value("${post.event.queue.size}")
    private int eventQueueSize;

    @Value("${thread.pool.size}")
    private int notifierThreadPoolSize;

    @Bean
    public BlockingQueue<PostEvent> getQueue() {
        return new ArrayBlockingQueue<>(eventQueueSize);
    }

    @Bean
    public ExecutorService getUserNotifierExecutorService() {
        return Executors.newFixedThreadPool(notifierThreadPoolSize);
    }

    @Bean
    public TaskScheduler taskScheduler() {
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(10);
        return new ConcurrentTaskScheduler(scheduledExecutorService);
    }
}
