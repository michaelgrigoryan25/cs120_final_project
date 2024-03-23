package models;

public class User {

    private Long id;
    private String firstName;
    private String secondName;
    private String username;

    public User(Long id, String firstName, String secondName, String username) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getSecondName() {
        return secondName;
    }

    public void setSecondName(String secondName) {
        this.secondName = secondName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
