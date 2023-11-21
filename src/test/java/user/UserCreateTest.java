package user;

import api.BaseTest;
import api.UserApiRequest;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.user.create.response.UserCreateErrorModel;
import model.user.create.response.UserCreateResponseModel;

import model.user.create.request.UserCreateRequestModel;
import model.user.delete.request.UserDeleteRequestModel;
import model.user.delete.response.UserDeleteResponseModel;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static generator.Generator.setRandomUserDataForCreate;
import static org.junit.Assert.*;

public class UserCreateTest
{
    private UserCreateRequestModel userCreateRequestModel;
    private UserApiRequest userApiRequest;
    private UserCreateResponseModel userCreateResponseModel;
    private BaseTest baseTest;

    @Before
    public void setUp() {
        userApiRequest = new UserApiRequest();
        userCreateRequestModel = setRandomUserDataForCreate();
    }
    @Step("Создание пользователя")
    @DisplayName("Создание пользователя")
    @Test
    public void createUser()
    {
        Response response = userApiRequest.createUser(userCreateRequestModel);
        userCreateResponseModel = response.as(UserCreateResponseModel.class);

        assertTrue(userCreateResponseModel.isSuccess());
        assertEquals(SC_OK, response.statusCode());



        baseTest = new BaseTest();
        baseTest.deleteUser(userCreateRequestModel.getEmail(), userCreateRequestModel.getPassword(), userCreateResponseModel.getAccessToken());
    }
    @Step("Создание уже существующего пользователя")
    @DisplayName("Создание уже существующего пользователя")
    @Test
    public void createExistingUser()
    {
        baseTest = new BaseTest();
        baseTest.createUser();

        UserCreateRequestModel existingUserCreateRequestModel = new UserCreateRequestModel(
                baseTest.getUserCreateRequestModel().getEmail(),
                baseTest.getUserCreateRequestModel().getPassword(),
                baseTest.getUserCreateRequestModel().getName());

        Response responseExistingUser = userApiRequest.createUser(existingUserCreateRequestModel);
        UserCreateErrorModel userCreateErrorModel = responseExistingUser.body().as(UserCreateErrorModel.class);

        assertFalse(userCreateErrorModel.isSuccess());
        assertEquals(SC_FORBIDDEN, responseExistingUser.statusCode());

        baseTest.deleteUserAfterLocalRegistration();
    }
    @Step("Создание пользователя с пустым именем")
    @DisplayName("Создание пользователя с пустым имененем")
    @Test
    public void createUserWithVoidName() {
        userCreateRequestModel.setName(null);

        Response response = userApiRequest.createUser(userCreateRequestModel);
        assertEquals(SC_FORBIDDEN, response.statusCode());
    }

    @Step("Создание пользователя с пустой почтой")
    @DisplayName("Создание пользователя с пустой почтой")
    @Test
    public void createUserWithVoidEmail() {
        userCreateRequestModel.setEmail(null);

        Response response = userApiRequest.createUser(userCreateRequestModel);
        assertEquals(SC_FORBIDDEN, response.statusCode());
    }

    @Step("Создание пользователя с пустым паролем")
    @DisplayName("Создание пользователя с пустым паролем")
    @Test
    public void createUserWithVoidPassword() {
        userCreateRequestModel.setPassword(null);

        Response response = userApiRequest.createUser(userCreateRequestModel);
        assertEquals(SC_FORBIDDEN, response.statusCode());
    }

}
