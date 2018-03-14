package database;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.UUID;

@XmlType(propOrder = {"id", "username", "password", "email", "journal"})
public class User {

    private Journal journal = null;
    private String username;
    private String password;
    private String email;
    private String id;

    public User() {
    }

    public User(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.id = UUID.randomUUID().toString();
        this.journal = new Journal();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getId() {
        return id;
    }

    public Journal getJournal() {
        return journal;
    }

    @XmlElement
    public void setJournal(Journal journal) {
        this.journal = journal;
    }

    @XmlElement
    public void setUsername(String username) {
        this.username = username;
    }

    @XmlElement
    public void setPassword(String password) {
        this.password = password;
    }

    @XmlElement
    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElement
    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "journal=" + journal +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}