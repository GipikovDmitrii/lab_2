package client.notification;

import client.gui.NotificationController;
import client.task.Task;

import javafx.collections.ObservableList;
import java.util.*;

public class Notification {

    private NotificationController controller;
    private List<Timer> timers;

    public Notification() {
        this.timers = new ArrayList<Timer>();
    }

    public void updateNotification(ObservableList<Task> tasks) {
        cancelTimers();
        for (Task task: tasks) {
            createNotification(task);
        }
    }

    private void createNotification(Task task) {
        long delay = (task.getEndTimeAsDate().getTime() - new Date().getTime());
        if (delay > 0) {
            Timer timer = new Timer(true);
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    controller = new NotificationController(task);
                    controller.showNotification();
                }
            }, delay);
            timers.add(timer);
        }
    }

    private void cancelTimers() {
        for (Timer timer: timers) {
            timer.cancel();
        }
        this.timers.clear();
    }
}