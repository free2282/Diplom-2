package model.user.update.response;

public class UserUpdateErrorModel
{
    private boolean success;
    private String message;


    public UserUpdateErrorModel() {
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
