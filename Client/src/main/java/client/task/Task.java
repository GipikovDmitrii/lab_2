package client.task;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Task {

    private int idTask;
    private String name;
    private String description;
    private String endTime;
    private String createdTime;

    public Task() {
    }

    public Task(String name, String description, String endTime) {
        this.name = name;
        this.description = description;
        this.endTime = endTime;
    }

    public Task(int id, String name, String description, String time, String createdTime) {
        this.idTask = id;
        this.name = name;
        this.description = description;
        this.endTime = time;
        this.createdTime = createdTime;
    }

    public int getTaskId() {
        return idTask;
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

    public void setTaskId(int idTask) {
        this.idTask = idTask;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Date getEndTimeAsDate() {
        try {
            return new SimpleDateFormat("hh:mm yyyy-MM-dd").parse(endTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}

