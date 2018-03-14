package client.handler;

import client.gui.NotificationController;
import client.notification.Notification;
import client.task.Task;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;


public class Handler {

    private static Handler handler;
    private Socket socket;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private String address;
    private int port;
    private String userId;
    //private Notification notification = new Notification();
    public ObservableList<Task> tasks = FXCollections.observableArrayList();

    private Handler() {
        connect();
    }

    public static Handler getHandler() {
        if (handler == null) {
            handler = new Handler();
        }
        return handler;
    }

    private void connect() {
        if (output == null && input == null) {
            try {
                address = ResourceBundle.getBundle("config").getString("address");
                port = Integer.parseInt(ResourceBundle.getBundle("config").getString("port"));
                socket = new Socket(address, port);
                output = new ObjectOutputStream(socket.getOutputStream());
                input = new ObjectInputStream(socket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean login(String username, String password) {
        JsonObject object = new JsonObject();
        try {
            object.add("command", new JsonPrimitive("login"));
            object.add("username", new JsonPrimitive(username));
            object.add("password", new JsonPrimitive(password));
            JsonObject o = sendAndGetAnswer(object.toString());
            if (o.get("command").getAsString().equals("success")) {
                userId = o.get("userId").getAsString();
                getTaskList(o);
            } else if (o.get("command").getAsString().equals("userNotFound")){
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }

    public void registration(String username, String password, String email) {
        JsonObject object = new JsonObject();
        try {
            object.add("command", new JsonPrimitive("registration"));
            object.add("username", new JsonPrimitive(username));
            object.add("password", new JsonPrimitive(password));
            object.add("email", new JsonPrimitive(email));
            sendAndGetAnswer(object.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void newTask(Task task) {
        JsonObject object = new JsonObject();
        try {
            object.add("command", new JsonPrimitive("newTask"));
            object.add("userId", new JsonPrimitive(userId));
            object.add("name", new JsonPrimitive(task.getName()));
            object.add("description", new JsonPrimitive(task.getDescription()));
            object.add("time", new JsonPrimitive(task.getEndTime()));
            getTaskList(sendAndGetAnswer(object.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteTask(int taskId) {
        JsonObject object = new JsonObject();
        try {
            object.add("command", new JsonPrimitive("deleteTask"));
            object.add("userId", new JsonPrimitive(userId));
            object.add("taskId", new JsonPrimitive(taskId));
            getTaskList(sendAndGetAnswer(object.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void getTaskList(JsonObject object) {
        clearTaskList();
        JsonArray array = object.get("tasks").getAsJsonArray();
        for (int i = 0; i < array.size(); i++) {
            JsonObject o = array.get(i).getAsJsonObject();
            Task task = new Task(
                    o.get("taskId").getAsInt(),
                    o.get("name").getAsString(),
                    o.get("description").getAsString(),
                    o.get("time").getAsString(),
                    o.get("createdTime").getAsString());
            tasks.add(task);
        }
    }

    public void rescheduleTask(int taskID, int time) {
        JsonObject object = new JsonObject();
        try {
            object.add("command", new JsonPrimitive("reschedule"));
            object.add("userId", new JsonPrimitive(userId));
            object.add("taskId", new JsonPrimitive(taskID));
            object.add("time", new JsonPrimitive(time));
            getTaskList(sendAndGetAnswer(object.toString()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void disconnect() {
        JsonObject object = new JsonObject();
        try {
            object.add("command", new JsonPrimitive("disconnect"));
            sendAndGetAnswer(object.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JsonObject sendAndGetAnswer(String o) throws IOException {
        connect();
        output.writeUTF(o);
        output.flush();
        Object object = input.readUTF();
        JsonParser parser = new JsonParser();
        return parser.parse((String)object).getAsJsonObject();
    }

    public ObservableList<Task> getTasks() {
        return tasks;
    }

    private void clearTaskList() {
        tasks.clear();
    }
}
