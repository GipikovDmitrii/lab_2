package database;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

@XmlType(propOrder = {"taskId", "name", "description", "time", "createdTime"})
public class Task {

    private int taskId;
    private String name;
    private String description;
    private String time;
    private String createdTime;

    public Task () {
    }

    public Task (int id, String name, String description, String time, String createdTime) {
        this.taskId = id;
        this.name = name;
        this.description = description;
        this.time = time;
        this.createdTime = createdTime;
    }

    public int getTaskId() {
        return taskId;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getTime() {
        return time;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    @XmlElement
    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    @XmlElement
    public void setName(String name) {
        this.name = name;
    }

    @XmlElement
    public void setDescription(String description) {
        this.description = description;
    }

    @XmlElement
    public void setTime(String time) {
        this.time = time;
    }

    @XmlElement
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", time='" + time + '\'' +
                ", createdTime='" + createdTime + '\'' +
                '}';
    }
}