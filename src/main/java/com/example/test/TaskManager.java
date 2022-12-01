package com.example.test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TaskManager {
    public List<ScheduledMessage> db = new ArrayList<>();
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
    public void runTask() {
        final Runnable printer = () -> {
            for (ScheduledMessage scheduledMessage : db) {
                LocalDateTime localDateTime = LocalDateTime.parse(scheduledMessage.getTime());
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String lt = formatter.format(localDateTime);
                String lt1 = formatter.format(LocalDateTime.now());
                if (lt.equals(lt1)) {
                    System.out.println("Message: " + scheduledMessage.getMessage() +  " printed at " + scheduledMessage.getTime());
                    db.remove(scheduledMessage);
                }
            }
        };
        scheduler.scheduleAtFixedRate(printer, 1, 1, TimeUnit.MINUTES);
    }

}
