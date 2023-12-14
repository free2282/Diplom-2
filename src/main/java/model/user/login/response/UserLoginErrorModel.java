package model.user.login.response;

public class UserLoginErrorModel {
    private boolean success;
    private String message;


    public UserLoginErrorModel() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
