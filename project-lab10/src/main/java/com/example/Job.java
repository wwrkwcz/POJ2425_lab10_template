package com.example;

import java.time.LocalDateTime;

public interface Job {
    void run();
    void setJobTime(LocalDateTime time);
    LocalDateTime getJobTime();
}