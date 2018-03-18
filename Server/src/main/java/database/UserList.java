package database;

import com.sun.istack.internal.Nullable;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "users")
public class UserList {

    @XmlElement(name = "user")
    private List<User> userList;

    public UserList() {
        this.userList = new ArrayList<User>();
    }

    public void addUser(User user) {
        userList.add(user);
    }

    @Nullable
    public User getUser(String username, String password) {
        for (User u : userList) {
            if (u.getUsername().equals(username) && u.getPassword().equals(password)) {
                return u;
            }
        }
        return null;
    }

    public User getUserById(String id) {
        for (User u : userList) {
            if (u.getId().equals(id)) {
                return u;
            }
        }
        return new User();
    }
}