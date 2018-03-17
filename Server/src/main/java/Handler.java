import com.google.gson.*;

import database.Task;
import database.UserList;
import database.XmlManager;
import database.User;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;

public class Handler implements Runnable {

    private Socket clientSocket;
    private ObjectInputStream inputStream;
    private ObjectOutputStream outputStream;
    private UserList userList;
    private XmlManager manager = new XmlManager();

    public Handler(Socket clientSocket) {
        this.clientSocket = clientSocket;
    }

    public void run() {
        System.out.println("new connection: " + Thread.currentThread().getId());
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
                break;
            case "login":
                if (isRegister(json.get("username").getAsString(), json.get("password").getAsString())) {
                    String userId = login(json.get("username").getAsString(), json.get("password").getAsString());
                    JsonArray array = getTaskList(userId);
                    answer.add("command", new JsonPrimitive("success"));
                    answer.add("userId", new JsonPrimitive(userId));
                    answer.add("tasks", array);
                } else {
                    answer.add("command", new JsonPrimitive("userNotFound"));
                }
                break;
            case "newTask": {
                addTask(
                        json.get("userId").getAsString(),
                        json.get("name").getAsString(),
                        json.get("description").getAsString(),
                        json.get("time").getAsString());
                JsonArray array = getTaskList(json.get("userId").getAsString());
                answer.add("tasks", array);
                break;
            }
            case "deleteTask": {
                String userId = json.get("userId").getAsString();
                int taskId = json.get("taskId").getAsInt();
                removeTask(userId, taskId);
                JsonArray array = getTaskList(userId);
                answer.add("tasks", array);
                break;
            }
            case "disconnect": {
                disconnect();
                break;
            }
            case "deleteAllTask": {
                deleteAllTask(json.get("userId").getAsString());
            }
        }
        sendAnswer(answer.toString());
    }

    private void registration(String username, String password, String email) {
        User user = new User(username, password, email);
        userList.addUser(user);
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

    private void rescheduleTask(int taskId, int time) {
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
            object.add("time", new JsonPrimitive(task.getTime()));
            object.add("createdTime", new JsonPrimitive(task.getCreatedTime()));
            array.add(object);
        }
        return array;
    }

    private void disconnect() {
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