package com.example;

public class JobThread extends Thread {
    private Job job;

    public JobThread(Job job) {
        this.job = job;
    }

    @Override
    public void run() {
        job.run();
    }
}