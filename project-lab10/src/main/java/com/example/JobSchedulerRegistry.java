package com.example;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JobSchedulerRegistry extends Thread{

    /**
     * Blok statyczny pozwala no inicjację pól statycznych
     */
    static{
        instance=new JobSchedulerRegistry();
    }

    private JobSchedulerRegistry(){}
    private static JobSchedulerRegistry instance;
    public static JobSchedulerRegistry getInstance(){return instance;}


    List<JobScheduler> schedulers = new ArrayList<>();

    public void register(JobScheduler scheduler) {
        schedulers.add(scheduler);
    }

    @Override
    public void run() {

        /**
         * Działamy w pętli nieskończonej
         */
        while(true){
            /**
             * tworzę event (zdarzenie) zmiany czasu
             */
            TimeEvent event = new TimeEvent();
            event.setTime(LocalDateTime.now());
            /**
             * Przechodzę przez wszystkie zadeklarowane harmonogramy (JobSchedulery)
             */
            for (JobScheduler scheduler : new ArrayList<>(schedulers)){
                /**
                 * każdy harmonogram (JobScheduler) jest obserwatorem
                 * na zdarzenia zmiany czasu.
                 * Tutaj należy dodać metodę 'listenTo(event)'
                 * w interfejsie 'JobSchedulera' i zaimplementować go w klasie
                 * SimpleJobScheduler tak oby, scheduler był 'świadomy'
                 * jaka godzina wybiła i w razie potrzeby uruchomił joba na oddzielnym wątku
                 */
                scheduler.listenTo(event);
            }
            try{
                    Thread.sleep(100);
            }
            catch(Exception ex){
                    ex.printStackTrace();
            }
        }
    }
}
