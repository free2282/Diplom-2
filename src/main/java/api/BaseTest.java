package api;

import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.user.create.request.UserCreateRequestModel;
import model.user.create.response.UserCreateResponseModel;
import model.user.delete.request.UserDeleteRequestModel;

import static generator.Generator.setRandomUserDataForCreate;

public class BaseTest {
    private UserApiRequest userApi;
    private UserCreateRequestModel userCreateRequestModel;
    private UserDeleteRequestModel userDeleteRequestModel;
    private String token;

    @Step("Cоздание пользователя")
    public void createUser() {
        userApi = new UserApiRequest();
        userCreateRequestModel = setRandomUserDataForCreate();
        Response response = userApi.createUser(userCreateRequestModel);
        UserCreateResponseModel userCreateResponseModel = response.body().as(UserCreateResponseModel.class);
        token = userCreateResponseModel.getAccessToken();
    }

    @Step("Удаление пользователя")
    @Description("Удаление пользователя в связке с регистрацией внутри одного теста")
    public void deleteUserAfterLocalRegistration() {
        userDeleteRequestModel = new UserDeleteRequestModel(
                userCreateRequestModel.getEmail(), userCreateRequestModel.getPassword()
        );

        userApi.deleteUser(userDeleteRequestModel, token);
    }

    @Step("Удаление пользователя")
    public void deleteUser(String email, String password, String token) {
        userApi = new UserApiRequest();
        userDeleteRequestModel = new UserDeleteRequestModel(email, password);
        userApi.deleteUser(userDeleteRequestModel, token);

    }

    public String getToken() {
        return token;
    }

    public UserCreateRequestModel getUserCreateRequestModel() {
        return userCreateRequestModel;
    }
}
