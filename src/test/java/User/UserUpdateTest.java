package User;
import api.UserApiRequest;
import com.codeborne.selenide.commands.As;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.user.create.request.UserCreateRequestModel;
import model.user.create.response.UserCreateResponseModel;
import model.user.delete.request.UserDeleteRequestModel;
import model.user.update.request.UserUpdateRequestModel;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static generator.Generator.setRandomUserDataForCreate;

public class UserUpdateTest
{
    private UserApiRequest userApiRequest;
    private UserCreateRequestModel userCreateRequestModel;
    private UserCreateResponseModel userCreateResponseModel;
    private UserUpdateRequestModel userUpdateRequestModel;
    private Response responseOfCreate;
    private String name;
    private String email;
    private String password;
    private String token;

    @Before
    public void setUp(){
        userApiRequest = new UserApiRequest();

        userCreateRequestModel = setRandomUserDataForCreate();
        responseOfCreate = userApiRequest.createUser(userCreateRequestModel);
        name = userCreateRequestModel.getName();
        email = userCreateRequestModel.getEmail();
        password = userCreateRequestModel.getPassword();

        userCreateResponseModel = responseOfCreate.body().as(UserCreateResponseModel.class);
        token = userCreateResponseModel.getAccessToken();
    }

    @DisplayName("изменение имени пользователя")
    @Description("создатеся пользователь, после чего достается его токен авторизации и передется в header запроса на обновление")
    @Test
    public void updateChangeNameTest()
    {
        userUpdateRequestModel = new UserUpdateRequestModel(email, password,name + "Собакевич");
        Response responseOfUpdate = userApiRequest.updateUser(userUpdateRequestModel, token);

        Assert.assertEquals(SC_OK, responseOfUpdate.statusCode());
    }

    @DisplayName("изменение пароля пользователя")
    @Description("создатеся пользователь, после чего достается его токен авторизации и передется в header запроса на обновление")
    @Test
    public void updateChangePasswordTest()
    {
        password = password + "123";
        userUpdateRequestModel = new UserUpdateRequestModel(email, password, name);
        Response responseOfUpdate = userApiRequest.updateUser(userUpdateRequestModel, token);

        Assert.assertEquals(SC_OK, responseOfUpdate.statusCode());
    }

    @DisplayName("изменение почты пользователя")
    @Description("создатеся пользователь, после чего достается его токен авторизации и передется в header запроса на обновление")
    @Test
    public void updateChangeEmailTest()
    {
        email = "change" + email;
        userUpdateRequestModel = new UserUpdateRequestModel(email, password, name);
        Response responseOfUpdate = userApiRequest.updateUser(userUpdateRequestModel, token);

        Assert.assertEquals(SC_OK, responseOfUpdate.statusCode());
    }

    @DisplayName("изменение имени пользователя без авторизации")
    @Description("ожидается 401 статус код")
    @Test
    public void updateChangeNameWithoutAccessTest()
    {
        userUpdateRequestModel = new UserUpdateRequestModel(email, password,name + "Собакевич");
        Response responseOfUpdate = userApiRequest.updateUserWithoutAccess(userUpdateRequestModel);

        Assert.assertEquals(SC_UNAUTHORIZED, responseOfUpdate.statusCode());
    }
    @DisplayName("изменение пароля пользователя без авторизации")
    @Description("ожидается 401 статус код")
    @Test
    public void updateChangePasswordWithoutAccessTest()
    {
        userUpdateRequestModel = new UserUpdateRequestModel(email, password + "123", name);
        Response responseOfUpdate = userApiRequest.updateUserWithoutAccess(userUpdateRequestModel);

        Assert.assertEquals(SC_UNAUTHORIZED, responseOfUpdate.statusCode());
    }

    @DisplayName("изменение почты пользователя без авторизации")
    @Description("ожидается 401 статус код")
    @Test
    public void updateChangeEmailWithoutAccessTest()
    {
        userUpdateRequestModel = new UserUpdateRequestModel(email+"change", password, name);
        Response responseOfUpdate = userApiRequest.updateUserWithoutAccess(userUpdateRequestModel);

        Assert.assertEquals(SC_UNAUTHORIZED, responseOfUpdate.statusCode());
    }

    @After
    public void setDown()
    {
        UserDeleteRequestModel userDeleteRequestModel = new UserDeleteRequestModel(email, password);
        Response responseDown = userApiRequest.deleteUser(userDeleteRequestModel, token);

        Assert.assertEquals(SC_ACCEPTED, responseDown.statusCode());
    }
}
