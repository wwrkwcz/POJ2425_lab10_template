package com.example;

import java.time.LocalDateTime;

public interface JobScheduler {
    JobScheduler forJob(Job job);
    JobScheduler startsAt(LocalDateTime time);
    JobScheduler everySeconds(int seconds);
    JobScheduler repeatTimes(int times);
    void listenTo(TimeEvent event);
}