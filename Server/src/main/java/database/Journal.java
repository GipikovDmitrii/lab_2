package database;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.util.ArrayList;
import java.util.List;

public class Journal {

    @XmlElement(name = "client.task")
    private List<Task> tasks;
    private int lastTaskId = 0;

    public Journal() {
        this.tasks = new ArrayList<Task>();
    }

    public int getTaskId() {
        return ++lastTaskId;
    }

    public int getLastTaskId() {
        return lastTaskId;
    }

    @XmlAttribute(name = "lastTaskId")
    public void setLastTaskId(int lastTaskId) {
        this.lastTaskId = lastTaskId;
    }

    public void addTask(Task task) {
        tasks.add(task);
    }

    public Task getTaskByIndex(int index) {
        return tasks.get(index);
    }

    public Task getTaskById(int taskId) {
        for (Task task : tasks) {
            if (task.getTaskId() == taskId) {
                return task;
            }
        }
        return new Task();
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public int size() {
        return tasks.size();
    }
}