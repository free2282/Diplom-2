package User;

import api.UserApiRequest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.user.create.response.UserCreateResponseModel;

import model.user.create.request.UserCreateRequestModel;
import model.user.delete.request.UserDeleteRequestModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static generator.Generator.setRandomUserDataForCreate;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class UserCreateTest {
    private UserCreateRequestModel userCreateRequestModel;
    private UserApiRequest userApiRequest;


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
        assertEquals(SC_OK, response.statusCode());

        UserCreateResponseModel userCreateResponseModel = response.as(UserCreateResponseModel.class);
        assertTrue(userCreateResponseModel.isSuccess());

        String token = userCreateResponseModel.getAccessToken();


        UserDeleteRequestModel userDeleteRequestModel = new UserDeleteRequestModel(userCreateRequestModel.getEmail(), userCreateRequestModel.getPassword());
        Response responseDown = userApiRequest.deleteUser(userDeleteRequestModel, token);

        Assert.assertEquals(SC_ACCEPTED, responseDown.statusCode());


    }

    @DisplayName("Создание уже существующего пользователя")
    @Test
    public void createExistingUser() {
        userApiRequest.createUser(userCreateRequestModel);
        Response response = userApiRequest.createUser(userCreateRequestModel);

        assertEquals(SC_FORBIDDEN, response.statusCode());
        System.out.println(response.statusCode());
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
