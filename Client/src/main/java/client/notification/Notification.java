package client.notification;

import client.gui.NotificationController;
import client.task.Task;

import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.*;

public class Notification {

    private NotificationController controller;

    public Notification() {
    }

    public void updateNotification(ObservableList<Task> tasks) {
        for (Task task: tasks) {
            createNotification(task);
        }
    }

    private void createNotification(Task task) {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                controller = new NotificationController(task);
                controller.showNotification();
            }
        }, task.getEndTimeAsDate());
    }
}