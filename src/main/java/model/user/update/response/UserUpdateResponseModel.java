package model.user.update.response;

public class UserUpdateResponseModel
{
    private boolean success;
    private User user;

    public UserUpdateResponseModel(boolean success, User user) {
        this.success = success;
        this.user = user;
    }

    public UserUpdateResponseModel() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
