package api;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.user.create.request.UserCreateRequestModel;
import model.user.delete.request.UserDeleteRequestModel;
import model.user.login.request.UserLoginRequestModel;
import model.user.update.request.UserUpdateRequestModel;

import static config.Config.*;

public class UserApiRequest extends BaseApi
{
    @Step("Отправка запроса на ручку по созданию пользователя")
    public Response createUser(UserCreateRequestModel userApiRequestModel)
    {
        return baseRequest()
                .body(userApiRequestModel)
                .post(MAIN_URL + CREATE_USER_URL);
    }
    @Step("Отправка запроса на ручку по входу пользователя")
    public Response loginUser(UserLoginRequestModel userLoginRequestModel)
    {
        return baseRequest()
                .body(userLoginRequestModel)
                .post(MAIN_URL + LOGIN_USER_URL);
    }
    @Step("Отправка запроса на ручку по обновлению пользователя с его токеном")
    public Response updateUser(UserUpdateRequestModel userUpdateRequestModel, String accessToken)
    {
        return baseRequest()
                .header("Authorization", accessToken)
                .body(userUpdateRequestModel)
                .patch(MAIN_URL + UPDATE_DELETE_USER_URL);

    }
    @Step("Отправка запроса на ручку по обновлению пользователя без токена")
    public Response updateUserWithoutAccess(UserUpdateRequestModel userUpdateRequestModel)
    {
        return baseRequest()
                .body(userUpdateRequestModel)
                .patch(MAIN_URL + UPDATE_DELETE_USER_URL);

    }
    @Step("Отправка запроса на ручку по удалению пользователя с его токеном")
    public Response deleteUser(UserDeleteRequestModel userDeleteRequestModel, String accessToken)
    {
        return baseRequest()
                .header("Authorization", accessToken)
                .body(userDeleteRequestModel)
                .delete(MAIN_URL + UPDATE_DELETE_USER_URL);
    }
}
