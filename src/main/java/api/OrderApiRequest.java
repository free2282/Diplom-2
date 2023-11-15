package api;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import model.order.create.OrderCreateModelRequest;

import static config.Config.CREATE_ORDER;
import static config.Config.MAIN_URL;

public class OrderApiRequest extends BaseApi
{
    @Step("Отправка запроса на ручку по созданию заказа")
    public Response createOrder(OrderCreateModelRequest orderCreateModelRequest, String accessToken) {
        return baseRequest()
                .header("Authorization", accessToken)
                .body(orderCreateModelRequest)
                .post(MAIN_URL + CREATE_ORDER);
    }

    @Step("Отправка запроса на ручку по создание заказа без авторизации")
    public Response createOrderWithoutLogin(OrderCreateModelRequest orderCreateModelRequest) {
        return baseRequest()
                .body(orderCreateModelRequest)
                .post(MAIN_URL + CREATE_ORDER);
    }
    @Step("Отправка запроса на ручку по получению заказа конкретного пользователя с его токеном")
    public Response getCurrentOrder(String accessToken) {
        return baseRequest()
                .header("Authorization", accessToken)
                .get(MAIN_URL + CREATE_ORDER);
    }
    @Step("Отправка запроса на ручку по получению заказа без авторизации")
    public Response getCurrentOrderWithoutAccess() {
        return baseRequest()
                .get(MAIN_URL + CREATE_ORDER);
    }
}
