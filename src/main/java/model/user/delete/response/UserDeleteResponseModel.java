package model.user.delete.response;

public class UserDeleteResponseModel
{
    private boolean success;
    private String message;


    public UserDeleteResponseModel() {
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
