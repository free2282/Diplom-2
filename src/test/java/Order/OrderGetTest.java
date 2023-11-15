package Order;
import static org.apache.http.HttpStatus.*;
import api.OrderApiRequest;
import api.UserApiRequest;
import com.codeborne.selenide.conditions.Or;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.order.create.OrderCreateModelRequest;
import model.user.create.request.UserCreateRequestModel;
import model.user.create.response.UserCreateResponseModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static generator.Generator.setRandomUserDataForCreate;

public class OrderGetTest
{
    private UserApiRequest userApiRequest;
    private OrderApiRequest orderApiRequest;
    private OrderCreateModelRequest orderCreateModelRequest;
    String token;


    @Before
    public void setUp()
    {
        String[] ingredients = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa78"};
        orderCreateModelRequest = new OrderCreateModelRequest(ingredients);

        UserCreateRequestModel userCreateRequestModel = setRandomUserDataForCreate();
        orderApiRequest = new OrderApiRequest();
        userApiRequest = new UserApiRequest();
        Response response = userApiRequest.createUser(userCreateRequestModel);

        UserCreateResponseModel userCreateResponseModel = response.body().as(UserCreateResponseModel.class);
        token = userCreateResponseModel.getAccessToken();
        orderApiRequest.createOrder(orderCreateModelRequest, token);
    }

    @DisplayName("Получение заказов пользователя")
    @Description("Генерируется пользователь, его заказ и происходи get заказа")
    @Test
    public void getCurrentUserOrders()
    {
        Response responseOfGetOrder = orderApiRequest.getCurrentOrder(token);
        Assert.assertEquals(SC_OK,responseOfGetOrder.statusCode());
    }

    @DisplayName("получение заказа не авторизированного пользователя")
    @Description("ожидается 400 ошибка")
    @Test
    public void getCurrentOrderWithoutLogin()
    {
        Response responseOfGetOrder = orderApiRequest.getCurrentOrderWithoutAccess();
        Assert.assertEquals(SC_UNAUTHORIZED,responseOfGetOrder.statusCode());
    }
}
