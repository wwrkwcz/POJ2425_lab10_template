package com.example;

import java.time.LocalDateTime;

public class DescribedJob implements Job {
    private String description;
    private LocalDateTime time;

    public DescribedJob(String description) {
        this.description = description;
    }

    @Override
    public void run() {
        System.out.println("DescribedJob (" + time + "): " + description);
    }

    @Override
    public void setJobTime(LocalDateTime time) {
        this.time = time;
    }

    @Override
    public LocalDateTime getJobTime() {
        return time;
    }
}