package order;
import static org.apache.http.HttpStatus.*;

import api.BaseTest;
import api.OrderApiRequest;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.order.create.OrderCreateRequestModel;
import model.order.get.OrderGetErrorModel;
import model.order.get.OrderGetResponseModel;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OrderGetTest
{
    private OrderApiRequest orderApiRequest;
    private OrderCreateRequestModel orderCreateRequestModel;
    private String token;
    private BaseTest baseTest;


    @Before
    public void setUp()
    {
        baseTest = new BaseTest();
        orderApiRequest = new OrderApiRequest();

        String[] ingredients = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa78"};
        orderCreateRequestModel = new OrderCreateRequestModel(ingredients);

        baseTest.createUser();
        token = baseTest.getToken();
        orderApiRequest.createOrder(orderCreateRequestModel, token);
    }

    @Step("Получение заказов пользователя")
    @DisplayName("Получение заказов пользователя")
    @Description("Генерируется пользователь, его заказ и происходи get заказа")
    @Test
    public void getCurrentUserOrders()
    {
        Response responseOfGetOrder = orderApiRequest.getCurrentOrder(token);
        OrderGetResponseModel orderGetResponseModel = responseOfGetOrder.body().as(OrderGetResponseModel.class);
        //основыне проверки
        assertTrue(orderGetResponseModel.isSuccess());
        assertEquals(SC_OK,responseOfGetOrder.statusCode());


        baseTest.deleteUserAfterLocalRegistration();
    }
    @Step("получение заказа не авторизированного пользователя")
    @DisplayName("получение заказа не авторизированного пользователя")
    @Description("ожидается 400 ошибка")
    @Test
    public void getCurrentOrderWithoutLogin()
    {
        Response responseOfGetOrder = orderApiRequest.getCurrentOrderWithoutAccess();
        OrderGetErrorModel orderGetErrorModel = responseOfGetOrder.body().as(OrderGetErrorModel.class);

        assertEquals(SC_UNAUTHORIZED,responseOfGetOrder.statusCode());
        assertFalse(orderGetErrorModel.isSuccess());
    }
}
