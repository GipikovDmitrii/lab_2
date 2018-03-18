import com.google.gson.*;

import database.Task;
import database.UserList;
import database.XmlManager;
import database.User;

import java.io.*;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Handler implements Runnable {

    private Socket clientSocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private UserList userList;
    private XmlManager manager;

    public Handler(Socket clientSocket) {
        this.manager = new XmlManager();
        this.clientSocket = clientSocket;
    }

    public void run() {
        try {
            userList = manager.loadXml();
            inputStream = new ObjectInputStream(clientSocket.getInputStream());
            outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
            String object;
            JsonParser parser = new JsonParser();
            while ((object = inputStream.readUTF()) != null) {
                process(parser.parse(object).getAsJsonObject());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void process(JsonObject json) {
        JsonObject answer = new JsonObject();
        switch (json.get("command").getAsString()) {
            case "registration":
                registration(
                        json.get("username").getAsString(),
                        json.get("email").getAsString(),
                        json.get("password").getAsString());
                saveData();
                break;
            case "login":
                if (isRegister(json.get("username").getAsString(), json.get("password").getAsString())) {
                    answer.add("command", new JsonPrimitive("success"));
                    answer.add("userId", new JsonPrimitive(login(json.get("username").getAsString(), json.get("password").getAsString())));
                    answer.add("tasks", getTaskList(login(json.get("username").getAsString(), json.get("password").getAsString())));
                } else {
                    answer.add("command", new JsonPrimitive("userNotFound"));
                }
                saveData();
                break;
            case "newTask": {
                addTask(
                        json.get("userId").getAsString(),
                        json.get("name").getAsString(),
                        json.get("description").getAsString(),
                        json.get("endTime").getAsString());
                answer.add("tasks", getTaskList(json.get("userId").getAsString()));
                saveData();
                break;
            }
            case "deleteTask": {
                removeTask(
                        json.get("userId").getAsString(),
                        json.get("taskId").getAsInt());
                answer.add("tasks", getTaskList(json.get("userId").getAsString()));
                saveData();
                break;
            }
            case "deleteAllTask": {
                deleteAllTask(json.get("userId").getAsString());
                answer.add(
                        "tasks",
                        getTaskList(json.get("userId").getAsString()));
                saveData();
                break;
            }
            case "reschedule": {
                rescheduleTask(
                        json.get("userId").getAsString(),
                        json.get("taskId").getAsInt(),
                        json.get("endTime").getAsString());
                answer.add("tasks", getTaskList(json.get("userId").getAsString()));
                saveData();
                break;
            }
            case "disconnect": {
                saveData();
                disconnect();
                break;
            }
        }
        sendAnswer(answer.toString());
    }

    private void registration(String username, String password, String email) {
        userList.addUser(new User(username, password, email));
    }

    private String login(String username, String password) {
        return userList.getUser(username, password).getId();
    }

    private boolean isRegister(String username, String password) {
        return userList.getUser(username, password) != null;
    }

    private void addTask(String userId, String name, String description, String time) {
        String createdTime = new SimpleDateFormat("h:mm a MM/dd/yyyy").format(System.currentTimeMillis());
        int id = userList.getUserById(userId).getJournal().getTaskId();
        userList.getUserById(userId).getJournal().addTask(new Task(id, name, description, time, createdTime));
    }

    private void removeTask(String userId, int id) {
        userList.getUserById(userId).getJournal().deleteTask(userList.getUserById(userId).getJournal().getTaskById(id));
    }

    private void rescheduleTask(String userId, int taskId, String time) {
        Task task = userList.getUserById(userId).getJournal().getTaskById(taskId);
        SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm MM-dd-yyyy");
        Calendar calendar = Calendar.getInstance();
        try {
            calendar.setTime(dateFormat.parse(task.getEndTime()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        switch (time) {
            case "fiveMin":
                calendar.add(Calendar.MINUTE, 5);
                task.setEndTime(dateFormat.format(calendar.getTime()));
                break;
            case "oneHour":
                calendar.add(Calendar.HOUR, 1);
                task.setEndTime(dateFormat.format(calendar.getTime()));
                break;
            case "oneDay":
                calendar.add(Calendar.DATE, 1);
                task.setEndTime(dateFormat.format(calendar.getTime()));
                break;
        }
    }

    private void deleteAllTask(String userId) {
        userList.getUserById(userId).getJournal().deleteAllTask();
    }

    private JsonArray getTaskList(String userId) {
        JsonArray array = new JsonArray();
        User user = userList.getUserById(userId);
        for (int i = 0; i < user.getJournal().size(); i++) {
            JsonObject object = new JsonObject();
            Task task = user.getJournal().getTaskByIndex(i);
            object.add("taskId", new JsonPrimitive(task.getTaskId()));
            object.add("name", new JsonPrimitive(task.getName()));
            object.add("description", new JsonPrimitive(task.getDescription()));
            object.add("endTime", new JsonPrimitive(task.getEndTime()));
            object.add("createdTime", new JsonPrimitive(task.getCreatedTime()));
            array.add(object);
        }
        return array;
    }

    private void disconnect() {
    }

    private void saveData() {
        manager.saveXml(userList);
    }

    private void sendAnswer(String object) {
        try {
            outputStream.writeUTF(object);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}