package com.example;

import java.time.LocalDateTime;

public class MainProgram {

    public static void main(String[] args){

        /**
         * Zacznijmy od utworzenia klasy 'TimeEvent'
         * która posiada jedno pole typu LocalDateTime
         * o nazwie time oraz gettery i settery do tego pola
         */
        TimeEvent event =new TimeEvent();
        event.setTime(LocalDateTime.now().plusSeconds(5));

        /**
         * Dodajmy teraz klasę o nazwie 'ShowTimeJob',
         * która w konstruktorze będzie przyjmować czas który będzie niedługo wyświetlać
         */
        ShowTimeJob showTimeJob = new ShowTimeJob(event.getTime());

        /**
         * Utwórz nowy interfejs o nazwie 'Job'
         * z metodami:
         * -> 'run' typu void - tutaj będzie zakodowane co robi dany job
         * -> 'setJobTime' typu void (setter do pola z czasem joba)
         * -> 'getJobTime' typu LocalDateTime (getter do pola z czasem joba)
         * Niech klasa ShowTimeJob implentuje ten interfejs,
         * tak aby metoda 'run' na ekranie konsoli wyświetlała czas podany w konstruktorze
         */
        Job job = (Job)showTimeJob;
        job.run();
        showTimeJob.setJobTime(LocalDateTime.now().plusSeconds(4));

        /**
         * Teraz utwórz klasę dziedziczącą po klasie Thread,
         * o nazwie 'JobThread', która w konstruktorze przyjmie
         * Joba i odpali go w nadpisanej metodzie 'run'.
         *
         * Metodą 'start' uruchomi joba na oddzielnym wątku (wzięte z klasy thread)
         */
        Thread jobThread = new JobThread(job);
        jobThread.start();

        /**
         * Utwórz nowy rodzaj Joba - 'DescribedJob',
         * gdzie w postaci napisu dajemy opis co ma się wydarzyć
         */
        Job jobWithDescription = new DescribedJob("Wykonuje zadanie");

        /**
         * Utwórz nowy interfejs o nazwie 'JobScheduler'
         * z zadeklarowanymi metodami:
         * -> forJob - ustawia joba do harmonogramu
         * -> everySeconds - ustawia odstęp czasowy w sekundach między kolejnymi wywołaniami joba
         * -> repeatTimes - ustawia ile razy ma się wykonać job, jeśli 0 to job wykonuje się bez końca
         *
         * klasa SimpleJobScheduler jest implementacją powyższego interfejsu,
         * za pomocą której tworzymy dwa różne harmonogramy zadań dla dwóch różnych job'ów
         */
        JobScheduler scheduler = new SimpleJobScheduler();
        scheduler.forJob(jobWithDescription)
                .startsAt(LocalDateTime.now().plusSeconds(5))
                .everySeconds(1)
                .repeatTimes(5);

        JobScheduler shedulerForTimeShow = new SimpleJobScheduler()
                .forJob(showTimeJob)
                .everySeconds(1);

        /**
         * klasa 'JobSchedulerRegistry'
         * jest singletonem, który zbiera w sobie zadeklarowane harmonogramy
         * zadań. Dziedziczy po klasie Thread i na oddzielnym wątku zaczyna pracować.
         * Dalej spójrz do implementacji klasy 'JobSchedulerRegistry'
         */
        JobSchedulerRegistry schedule = JobSchedulerRegistry.getInstance();

        schedule.register(scheduler);
        schedule.register(shedulerForTimeShow);
        schedule.start();

    }
}
