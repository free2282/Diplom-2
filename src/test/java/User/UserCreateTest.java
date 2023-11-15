package User;

import api.UserApiRequest;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.user.create.response.UserCreateErrorModel;
import model.user.create.response.UserCreateResponseModel;

import model.user.create.request.UserCreateRequestModel;
import model.user.delete.request.UserDeleteRequestModel;
import model.user.delete.response.UserDeleteResponseModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static generator.Generator.setRandomUserDataForCreate;
import static org.junit.Assert.*;

public class UserCreateTest
{
    private UserCreateRequestModel userCreateRequestModel;
    private UserApiRequest userApiRequest;
    private UserDeleteRequestModel userDeleteRequestModel;
    private UserDeleteResponseModel userDeleteResponseModel;
    private UserCreateResponseModel userCreateResponseModel;

    @Before
    public void setUp() {
        userApiRequest = new UserApiRequest();
        userCreateRequestModel = setRandomUserDataForCreate();
    }

    @DisplayName("Создание пользователя")
    @Test
    public void createUser()
    {
        Response response = userApiRequest.createUser(userCreateRequestModel);
        userCreateResponseModel = response.as(UserCreateResponseModel.class);

        assertTrue(userCreateResponseModel.isSuccess());
        assertEquals(SC_OK, response.statusCode());


        String token = userCreateResponseModel.getAccessToken();
        userDeleteRequestModel = new UserDeleteRequestModel(userCreateRequestModel.getEmail(), userCreateRequestModel.getPassword());
        Response responseDown = userApiRequest.deleteUser(userDeleteRequestModel, token);
        userDeleteResponseModel = responseDown.body().as(UserDeleteResponseModel.class);

        assertEquals(SC_ACCEPTED, responseDown.statusCode());
        Assert.assertTrue(userDeleteResponseModel.isSuccess());
    }

    @DisplayName("Создание уже существующего пользователя")
    @Test
    public void createExistingUser() {
        Response response = userApiRequest.createUser(userCreateRequestModel);
        userCreateResponseModel = response.body().as(UserCreateResponseModel.class);
        String token = userCreateResponseModel.getAccessToken();

        assertEquals(SC_OK, response.statusCode());
        assertTrue(userCreateResponseModel.isSuccess());


        Response responseExistingUser = userApiRequest.createUser(userCreateRequestModel);
        UserCreateErrorModel userCreateErrorModel = responseExistingUser.body().as(UserCreateErrorModel.class);

        assertFalse(userCreateErrorModel.isSuccess());
        assertEquals(SC_FORBIDDEN, responseExistingUser.statusCode());

        userDeleteRequestModel = new UserDeleteRequestModel(userCreateRequestModel.getEmail(), userCreateRequestModel.getPassword());
        Response responseDeleteExistingUser = userApiRequest.deleteUser(userDeleteRequestModel, token);
        userDeleteResponseModel = responseDeleteExistingUser.body().as(UserDeleteResponseModel.class);

        assertTrue(userDeleteResponseModel.isSuccess());
        assertEquals(SC_ACCEPTED, responseDeleteExistingUser.statusCode());
    }

    @DisplayName("Создание пользователя с пустым имененем")
    @Test
    public void createUserWithVoidName() {
        userCreateRequestModel.setName(null);

        Response response = userApiRequest.createUser(userCreateRequestModel);
        assertEquals(SC_FORBIDDEN, response.statusCode());
    }

    @DisplayName("Создание пользователя с пустой почтой")
    @Test
    public void createUserWithVoidEmail() {
        userCreateRequestModel.setEmail(null);

        Response response = userApiRequest.createUser(userCreateRequestModel);
        assertEquals(SC_FORBIDDEN, response.statusCode());
    }

    @DisplayName("Создание пользователя с пустым паролем")
    @Test
    public void createUserWithVoidPassword() {
        userCreateRequestModel.setPassword(null);

        Response response = userApiRequest.createUser(userCreateRequestModel);
        assertEquals(SC_FORBIDDEN, response.statusCode());
    }

}
