package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.order.create.OrderCreateRequestModel;

import static config.Config.CREATE_ORDER;
import static config.Config.MAIN_URL;

public class OrderApiRequest extends BaseApi
{
    @Step("Отправка запроса на ручку по созданию заказа")
    public Response createOrder(OrderCreateRequestModel orderCreateRequestModel, String accessToken) {
        return baseRequest()
                .header("Authorization", accessToken)
                .body(orderCreateRequestModel)
                .post(CREATE_ORDER);
    }

    @Step("Отправка запроса на ручку по создание заказа без авторизации")
    public Response createOrderWithoutLogin(OrderCreateRequestModel orderCreateRequestModel) {
        return baseRequest()
                .body(orderCreateRequestModel)
                .post(CREATE_ORDER);
    }
    @Step("Отправка запроса на ручку по получению заказа конкретного пользователя с его токеном")
    public Response getCurrentOrder(String accessToken) {
        return baseRequest()
                .header("Authorization", accessToken)
                .get(CREATE_ORDER);
    }
    @Step("Отправка запроса на ручку по получению заказа без авторизации")
    public Response getCurrentOrderWithoutAccess() {
        return baseRequest()
                .get(CREATE_ORDER);
    }
}
