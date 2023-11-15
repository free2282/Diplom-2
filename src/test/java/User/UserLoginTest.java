package User;

import api.UserApiRequest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;

import model.user.create.request.UserCreateRequestModel;
import model.user.create.response.UserCreateResponseModel;
import model.user.delete.request.UserDeleteRequestModel;
import model.user.delete.response.UserDeleteResponseModel;
import model.user.login.request.UserLoginRequestModel;
import model.user.login.response.UserLoginErrorModel;
import model.user.login.response.UserLoginResponseModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static generator.Generator.setRandomUserDataForCreate;
import static org.junit.Assert.*;

public class UserLoginTest
{
    private UserApiRequest userApiRequest;
    private UserCreateRequestModel userCreateRequestModel;
    private UserCreateResponseModel userCreateResponseModel;
    private UserLoginRequestModel userLoginRequestModel;
    private UserLoginErrorModel userLoginErrorModel;
    private UserLoginResponseModel userLoginResponseModel;
    private String emailForLoginTest;
    private String passwordForLoginTest;
    private String name;
    Response response;
    Response responseOfCrete;
    private String token;

    @Before
    public void setUp()
    {
        userApiRequest = new UserApiRequest();
        userCreateRequestModel = setRandomUserDataForCreate();
        responseOfCrete = userApiRequest.createUser(userCreateRequestModel);

        emailForLoginTest = userCreateRequestModel.getEmail();
        passwordForLoginTest = userCreateRequestModel.getPassword();
    }

    @DisplayName("авторизация пользователя")
    @Description("создатеся пользователь, после чего достается его токен авторизации и передется в header запроса на авторизацию")
    @Test
    public void loginTest()
    {
        userLoginRequestModel = new UserLoginRequestModel(emailForLoginTest, passwordForLoginTest);
        response = userApiRequest.loginUser(userLoginRequestModel);
        userLoginResponseModel = response.body().as(UserLoginResponseModel.class);

        assertTrue(userLoginResponseModel.isSuccess());
        assertEquals(SC_OK, response.statusCode());
    }

    @DisplayName("авторизация пользователя с ошибочной почтой")
    @Test
    public void loginTestWithErrorEmail()
    {
        userLoginRequestModel = new UserLoginRequestModel("error" + emailForLoginTest, passwordForLoginTest);

        response = userApiRequest.loginUser(userLoginRequestModel);
        userLoginErrorModel = response.body().as(UserLoginErrorModel.class);

        assertEquals(SC_UNAUTHORIZED, response.statusCode());
        assertFalse(userLoginErrorModel.isSuccess());
    }

    @DisplayName("авторизация пользователя с ошибочным паролем")
    @Test
    public void loginTestWithErrorPassword()
    {
        userLoginRequestModel = new UserLoginRequestModel(emailForLoginTest, "error" + passwordForLoginTest);

        response = userApiRequest.loginUser(userLoginRequestModel);
        userLoginErrorModel = response.body().as(UserLoginErrorModel.class);

        assertEquals(SC_UNAUTHORIZED, response.statusCode());
        assertFalse(userLoginErrorModel.isSuccess());
    }

    @After
    public void setDown()
    {
        userCreateResponseModel = responseOfCrete.body().as(UserCreateResponseModel.class);
        token = userCreateResponseModel.getAccessToken();


        UserDeleteRequestModel userDeleteRequestModel = new UserDeleteRequestModel(emailForLoginTest, passwordForLoginTest);
        Response responseDown = userApiRequest.deleteUser(userDeleteRequestModel, token);
        UserDeleteResponseModel userDeleteResponseModel = responseDown.body().as(UserDeleteResponseModel.class);

        assertEquals(SC_ACCEPTED, responseDown.statusCode());
        assertTrue(userDeleteResponseModel.isSuccess());
    }
}
