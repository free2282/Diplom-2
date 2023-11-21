package model.user.login.response;

import model.user.create.response.UserCreate;

public class UserLoginResponseModel {
    private Boolean success;
    private UserCreate userCreate;
    private String accessToken;
    public String refreshToken;

    public UserLoginResponseModel(boolean success, UserCreate userCreate, String accessToken, String refreshToken) {
        this.success = success;
        this.userCreate = userCreate;
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
    }

    public UserLoginResponseModel() {
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public UserCreate getUser() {
        return userCreate;
    }

    public void setUser(UserCreate userCreate) {
        this.userCreate = userCreate;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
