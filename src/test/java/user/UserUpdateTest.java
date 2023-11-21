package user;
import api.BaseTest;
import api.UserApiRequest;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.user.create.request.UserCreateRequestModel;
import model.user.create.response.UserCreateResponseModel;
import model.user.delete.request.UserDeleteRequestModel;
import model.user.delete.response.UserDeleteResponseModel;
import model.user.update.request.UserUpdateRequestModel;
import model.user.update.response.UserUpdateErrorModel;
import model.user.update.response.UserUpdateResponseModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.apache.http.HttpStatus.*;
import static generator.Generator.setRandomUserDataForCreate;
import static org.junit.Assert.assertFalse;

public class UserUpdateTest
{
    private UserApiRequest userApiRequest;
    private UserUpdateRequestModel userUpdateRequestModel;
    private String name;
    private String email;
    private String password;
    private BaseTest baseTest;

    @Before
    public void setUp()
    {
        userApiRequest = new UserApiRequest();
        baseTest = new BaseTest();
        baseTest.createUser();

        name = baseTest.getUserCreateRequestModel().getName();
        password = baseTest.getUserCreateRequestModel().getPassword();
        email = baseTest.getUserCreateRequestModel().getEmail();
    }

    @Step("Изменение имени пользователя")
    @DisplayName("изменение имени пользователя")
    @Description("создатеся пользователь, после чего достается его токен авторизации и передется в header запроса на обновление")
    @Test
    public void updateChangeNameTest()
    {
        name = "Собакевич";
        userUpdateRequestModel = new UserUpdateRequestModel(email, password,name);
        Response responseOfUpdate = userApiRequest.updateUser(userUpdateRequestModel, baseTest.getToken());
        UserUpdateResponseModel userUpdateResponseModel = responseOfUpdate.body().as(UserUpdateResponseModel.class);

        assertTrue(userUpdateResponseModel.isSuccess());
        assertEquals(SC_OK, responseOfUpdate.statusCode());
    }

    @Step("Изменение пароля пользователя")
    @DisplayName("изменение пароля пользователя")
    @Description("создатеся пользователь, после чего достается его токен авторизации и передется в header запроса на обновление")
    @Test
    public void updateChangePasswordTest()
    {
        password = password + "123";
        userUpdateRequestModel = new UserUpdateRequestModel(email, password, name);
        Response responseOfUpdate = userApiRequest.updateUser(userUpdateRequestModel, baseTest.getToken());
        UserUpdateResponseModel userUpdateResponseModel = responseOfUpdate.body().as(UserUpdateResponseModel.class);

        assertTrue(userUpdateResponseModel.isSuccess());
        assertEquals(SC_OK, responseOfUpdate.statusCode());
    }

    @Step("Изменение почты пользователя")
    @DisplayName("изменение почты пользователя")
    @Description("создатеся пользователь, после чего достается его токен авторизации и передется в header запроса на обновление")
    @Test
    public void updateChangeEmailTest()
    {
        email = "change" + email;
        userUpdateRequestModel = new UserUpdateRequestModel(email, password, name);
        Response responseOfUpdate = userApiRequest.updateUser(userUpdateRequestModel, baseTest.getToken());
        UserUpdateResponseModel userUpdateResponseModel = responseOfUpdate.body().as(UserUpdateResponseModel.class);

        assertTrue(userUpdateResponseModel.isSuccess());
        assertEquals(SC_OK, responseOfUpdate.statusCode());
    }

    @Step("Изменение имени пользователя без авторизации")
    @DisplayName("изменение имени пользователя без авторизации")
    @Description("ожидается 401 статус код")
    @Test
    public void updateChangeNameWithoutAccessTest()
    {
        userUpdateRequestModel = new UserUpdateRequestModel(email, password,name + "Собакевич");
        Response responseOfUpdate = userApiRequest.updateUserWithoutAccess(userUpdateRequestModel);
        UserUpdateErrorModel userUpdateErrorModel = responseOfUpdate.body().as(UserUpdateErrorModel.class);

        assertEquals(SC_UNAUTHORIZED, responseOfUpdate.statusCode());
        assertFalse(userUpdateErrorModel.isSuccess());
    }
    @Step("Изменение пароля пользователя без авторизации")
    @DisplayName("изменение пароля пользователя без авторизации")
    @Description("ожидается 401 статус код")
    @Test
    public void updateChangePasswordWithoutAccessTest()
    {
        userUpdateRequestModel = new UserUpdateRequestModel(email, password + "123", name);
        Response responseOfUpdate = userApiRequest.updateUserWithoutAccess(userUpdateRequestModel);
        UserUpdateErrorModel userUpdateErrorModel = responseOfUpdate.body().as(UserUpdateErrorModel.class);

        assertEquals(SC_UNAUTHORIZED, responseOfUpdate.statusCode());
        assertFalse(userUpdateErrorModel.isSuccess());
    }

    @Step("Изменение почты пользователя без авторизации")
    @DisplayName("изменение почты пользователя без авторизации")
    @Description("ожидается 401 статус код")
    @Test
    public void updateChangeEmailWithoutAccessTest()
    {
        userUpdateRequestModel = new UserUpdateRequestModel(email+"change", password, name);
        Response responseOfUpdate = userApiRequest.updateUserWithoutAccess(userUpdateRequestModel);
        UserUpdateErrorModel userUpdateErrorModel = responseOfUpdate.body().as(UserUpdateErrorModel.class);

        assertEquals(SC_UNAUTHORIZED, responseOfUpdate.statusCode());
        assertFalse(userUpdateErrorModel.isSuccess());
    }

    @After
    public void setDown()
    {
        baseTest.deleteUserAfterLocalRegistration();
    }
}
