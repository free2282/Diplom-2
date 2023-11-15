package Order;
import static org.apache.http.HttpStatus.*;
import api.OrderApiRequest;
import api.UserApiRequest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.order.create.OrderCreateErrorModel;
import model.order.create.OrderCreateRequestModel;
import model.order.create.OrderCreateResponseModel;
import model.user.create.request.UserCreateRequestModel;
import model.user.create.response.UserCreateResponseModel;
import model.user.delete.request.UserDeleteRequestModel;
import model.user.delete.response.UserDeleteResponseModel;
import model.user.login.request.UserLoginRequestModel;
import org.junit.Before;
import org.junit.Test;

import static generator.Generator.setRandomUserDataForCreate;
import static org.junit.Assert.*;

public class OrderCreateTest
{
    private UserApiRequest userApiRequest;
    private UserCreateRequestModel userCreateRequestModel;
    private OrderApiRequest orderApiRequest;
    private UserLoginRequestModel userLoginRequestModel;
    private OrderCreateRequestModel orderCreateRequestModel;
    private Response response;


    @Before
    public void setUp()
    {
        orderApiRequest = new OrderApiRequest();
        String[] ingredients = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa78"};
        orderCreateRequestModel = new OrderCreateRequestModel(ingredients);
    }

    @DisplayName("Создание заказа с залогиненным пользователем")
    @Description("В header заказа добавляется токен авторизации")
    @Test
    public void creteOrderWithLoginTest()
    {
        userApiRequest = new UserApiRequest();
        userCreateRequestModel = setRandomUserDataForCreate();
        response = userApiRequest.createUser(userCreateRequestModel);
        UserCreateResponseModel userCreateResponseModel = response.body().as(UserCreateResponseModel.class);
        String token = userCreateResponseModel.getAccessToken();

        assertEquals(SC_OK, response.statusCode());
        assertTrue(userCreateResponseModel.isSuccess());

        Response responseOfOrder =  orderApiRequest.createOrder(orderCreateRequestModel, token);
        OrderCreateResponseModel orderCreateResponseModel = responseOfOrder.body().as(OrderCreateResponseModel.class);
        //основная проверка создания
        assertTrue(orderCreateResponseModel.isSuccess());
        assertEquals(SC_OK, responseOfOrder.statusCode());

        UserDeleteRequestModel userDeleteRequestModel = new UserDeleteRequestModel(
                userCreateRequestModel.getEmail(), userCreateRequestModel.getPassword()
        );
        Response responseOfDelete = userApiRequest.deleteUser(userDeleteRequestModel, token);
        UserDeleteResponseModel userDeleteResponseModel = responseOfDelete.body().as(UserDeleteResponseModel.class);

        assertTrue(userDeleteResponseModel.isSuccess());
        assertEquals(SC_ACCEPTED, responseOfDelete.statusCode());
    }

    @DisplayName("Создание заказа без логина в систему")
    @Test
    public void createOrderWithoutLogin()
    {
        Response responseOfOrder =  orderApiRequest.createOrderWithoutLogin(orderCreateRequestModel);
        OrderCreateResponseModel orderCreateResponseModel = responseOfOrder.body().as(OrderCreateResponseModel.class);

        assertTrue(orderCreateResponseModel.isSuccess());
        assertEquals(SC_OK, responseOfOrder.statusCode());
    }

    @DisplayName("Создание заказа без ингридиентов в корзине")
    @Description("В запросе не указываются ингридиенты, поэтому должна упасть 400 ошибка")
    @Test
    public void createOrderWithoutIngredients()
    {
        String[] voidIngredients = {};
        orderCreateRequestModel.setIngredients(voidIngredients);

        Response responseOfOrder = orderApiRequest.createOrderWithoutLogin(orderCreateRequestModel);
        OrderCreateErrorModel orderCreateErrorModel = responseOfOrder.body().as(OrderCreateErrorModel.class);

        assertEquals(SC_BAD_REQUEST, responseOfOrder.statusCode());
        assertFalse(orderCreateErrorModel.isSuccess());
    }

    @DisplayName("Создание заказа с ошибочными ингридиентами в корзине")
    @Description("В запросе не указываются неправильные ингридиенты, поэтому должна упасть 500 ошибка")
    @Test
    public void createOrderWithErrorIdFoods()
    {
        String[] voidIngredients = {"61c0c5a71d1f82001bdaaa6d2573regw", "61c0c5a71d1f82001bdaaa6fqwevfwer", "61c0c5a71d1f82001bdaaa78wfererwg"};
        orderCreateRequestModel.setIngredients(voidIngredients);

        Response responseOfOrder =  orderApiRequest.createOrderWithoutLogin(orderCreateRequestModel);
        assertEquals(SC_INTERNAL_SERVER_ERROR, responseOfOrder.statusCode());
    }
}
