package User;

import api.UserApiRequest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;

import model.user.create.request.UserCreateRequestModel;
import model.user.create.response.UserCreateResponseModel;
import model.user.delete.request.UserDeleteRequestModel;
import model.user.login.request.UserLoginRequestModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static generator.Generator.setRandomUserDataForCreate;

public class UserLoginTest
{
    private UserApiRequest userApiRequest;
    private UserCreateRequestModel userCreateRequestModel;
    private UserCreateResponseModel userCreateResponseModel;
    private UserLoginRequestModel userLoginRequestModel;
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

        Assert.assertEquals(SC_OK, response.statusCode());
    }

    @DisplayName("авторизация пользователя с ошыбочной почтой")
    @Test
    public void loginTestWithErrorEmail()
    {
        userLoginRequestModel = new UserLoginRequestModel("error" + emailForLoginTest, passwordForLoginTest);

        response = userApiRequest.loginUser(userLoginRequestModel);
        Assert.assertEquals(SC_UNAUTHORIZED, response.statusCode());
    }

    @DisplayName("авторизация пользователя с ошыбочным паролем")
    @Test
    public void loginTestWithErrorPassword()
    {
        userLoginRequestModel = new UserLoginRequestModel(emailForLoginTest, "error" + passwordForLoginTest);

        response = userApiRequest.loginUser(userLoginRequestModel);
        Assert.assertEquals(SC_UNAUTHORIZED, response.statusCode());
    }

    @After
    public void setDown()
    {
        userCreateResponseModel = responseOfCrete.body().as(UserCreateResponseModel.class);
        token = userCreateResponseModel.getAccessToken();


        UserDeleteRequestModel userDeleteRequestModel = new UserDeleteRequestModel(emailForLoginTest, passwordForLoginTest);
        Response responseDown = userApiRequest.deleteUser(userDeleteRequestModel, token);

        Assert.assertEquals(SC_ACCEPTED, responseDown.statusCode());
    }
}
