package com.example;

import java.time.LocalDateTime;

public class SimpleJobScheduler implements JobScheduler {
    private Job job;
    private LocalDateTime nextRunTime = LocalDateTime.now();
    private int intervalSeconds = 0;
    private int repeatTimes = 0;
    private int executedTimes = 0;
    private boolean isFinished = false;

    @Override
    public JobScheduler forJob(Job job) {
        this.job = job;
        return this;
    }

    @Override
    public JobScheduler startsAt(LocalDateTime time) {
        this.nextRunTime = time;
        return this;
    }

    @Override
    public JobScheduler everySeconds(int seconds) {
        this.intervalSeconds = seconds;
        return this;
    }

    @Override
    public JobScheduler repeatTimes(int times) {
        this.repeatTimes = times;
        return this;
    }

    @Override
    public void listenTo(TimeEvent event) {
        if (isFinished || job == null) return;

        LocalDateTime currentTime = event.getTime();

        if (!currentTime.isBefore(nextRunTime)) {
            job.setJobTime(currentTime);

            JobThread thread = new JobThread(job);
            thread.start();

            executedTimes++;

            if (repeatTimes > 0 && executedTimes >= repeatTimes) {
                isFinished = true;
            } else {
                nextRunTime = nextRunTime.plusSeconds(intervalSeconds);
            }
        }
    }
}