package database;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@XmlType(propOrder = {"taskId", "name", "description", "endTime", "createdTime"})
public class Task {

    private int taskId;
    private String name;
    private String description;
    private String endTime;
    private String createdTime;

    public Task () {
    }

    public Task (int id, String name, String description, String endTime, String createdTime) {
        this.taskId = id;
        this.name = name;
        this.description = description;
        this.endTime = endTime;
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

    public String getEndTime() {
        return endTime;
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
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @XmlElement
    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public Date getEndTimeAsDate() {
        try {
            return new SimpleDateFormat("hh:mm yyyy-MM-dd").parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskId=" + taskId +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", endTime='" + endTime + '\'' +
                ", createdTime='" + createdTime + '\'' +
                '}';
    }
}