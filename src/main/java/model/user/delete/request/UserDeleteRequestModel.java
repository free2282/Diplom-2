package model.user.delete.request;

public class UserDeleteRequestModel {
    private String email;
    private String password;

    public UserDeleteRequestModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
