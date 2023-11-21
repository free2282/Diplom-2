package model.user.login.response;

public class UserLogin {
    private String email;
    private String name;

    public UserLogin(String email, String name) {
        this.email = email;
        this.name = name;
    }

    public UserLogin() {
    }

    public String getEmail() {
        return email;
    }


    public String getName() {
        return name;
    }
}
