package Order;
import static org.apache.http.HttpStatus.*;
import api.OrderApiRequest;
import api.UserApiRequest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.order.create.OrderCreateRequestModel;
import model.order.get.OrderGetErrorModel;
import model.order.get.OrderGetResponseModel;
import model.user.create.request.UserCreateRequestModel;
import model.user.create.response.UserCreateResponseModel;
import model.user.delete.request.UserDeleteRequestModel;
import model.user.delete.response.UserDeleteResponseModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static generator.Generator.setRandomUserDataForCreate;
import static org.junit.Assert.*;

public class OrderGetTest
{
    private UserApiRequest userApiRequest;
    private OrderApiRequest orderApiRequest;
    private OrderCreateRequestModel orderCreateRequestModel;
    private UserCreateRequestModel userCreateRequestModel;
    String token;


    @Before
    public void setUp()
    {
        String[] ingredients = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa78"};
        orderCreateRequestModel = new OrderCreateRequestModel(ingredients);

        userCreateRequestModel = setRandomUserDataForCreate();
        orderApiRequest = new OrderApiRequest();
        userApiRequest = new UserApiRequest();
        Response response = userApiRequest.createUser(userCreateRequestModel);

        UserCreateResponseModel userCreateResponseModel = response.body().as(UserCreateResponseModel.class);
        token = userCreateResponseModel.getAccessToken();
        orderApiRequest.createOrder(orderCreateRequestModel, token);
    }

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


        UserDeleteRequestModel userDeleteRequestModel = new UserDeleteRequestModel(
                userCreateRequestModel.getEmail(), userCreateRequestModel.getPassword()
        );
        Response responseDown = userApiRequest.deleteUser(userDeleteRequestModel, token);
        UserDeleteResponseModel userDeleteResponseModel = responseDown.body().as(UserDeleteResponseModel.class);

        assertEquals(SC_ACCEPTED, responseDown.statusCode());
        assertTrue(userDeleteResponseModel.isSuccess());
    }

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
