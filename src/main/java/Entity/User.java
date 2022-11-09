package Entity;

public class User {
    private static int idCounter = 10000;
    private final int id;
    private String email;
    private String name;
    private String password;

    public User(String email, String name, String password) {
        this.id = idCounter++;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}