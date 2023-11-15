package Order;
import static org.apache.http.HttpStatus.*;
import api.OrderApiRequest;
import api.UserApiRequest;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import model.order.create.OrderCreateModelRequest;
import model.user.create.request.UserCreateRequestModel;
import model.user.create.response.UserCreateResponseModel;
import model.user.login.request.UserLoginRequestModel;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static generator.Generator.setRandomUserDataForCreate;

public class OrderCreateTest
{
    private UserApiRequest userApiRequest;
    private UserCreateRequestModel userCreateRequestModel;
    private OrderApiRequest orderApiRequest;
    private UserLoginRequestModel userLoginRequestModel;
    private OrderCreateModelRequest orderCreateModelRequest;
    private Response response;
    private String email;
    private String password;


    @Before
    public void setUp()
    {
        orderApiRequest = new OrderApiRequest();
        String[] ingredients = {"61c0c5a71d1f82001bdaaa6d", "61c0c5a71d1f82001bdaaa6f", "61c0c5a71d1f82001bdaaa78"};
        orderCreateModelRequest = new OrderCreateModelRequest(ingredients);
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

        Response responseOfOrder =  orderApiRequest.createOrder(orderCreateModelRequest, token);
        Assert.assertEquals(SC_OK, responseOfOrder.statusCode());
    }

    @DisplayName("Создание заказа без логина в систему")
    @Test
    public void createOrderWithoutLogin()
    {
        Response responseOfOrder =  orderApiRequest.createOrderWithoutLogin(orderCreateModelRequest);
        Assert.assertEquals(SC_OK, responseOfOrder.statusCode());
    }

    @DisplayName("Создание заказа без ингридиентов в корзине")
    @Description("В запросе не указываются ингридиенты, поэтому должна упасть 400 ошибка")
    @Test
    public void createOrderWithoutIngredients()
    {
        String[] voidIngredients = {};
        orderCreateModelRequest.setIngredients(voidIngredients);

        Response responseOfOrder =  orderApiRequest.createOrderWithoutLogin(orderCreateModelRequest);
        Assert.assertEquals(SC_BAD_REQUEST, responseOfOrder.statusCode());
    }

    @DisplayName("Создание заказа с ошибочными ингридиентами в корзине")
    @Description("В запросе не указываются неправильные ингридиенты, поэтому должна упасть 500 ошибка")
    @Test
    public void createOrderWithErrorIdFoods()
    {
        String[] voidIngredients = {"61c0c5a71d1f82001bdaaa6d2573regw", "61c0c5a71d1f82001bdaaa6fqwevfwer", "61c0c5a71d1f82001bdaaa78wfererwg"};
        orderCreateModelRequest.setIngredients(voidIngredients);

        Response responseOfOrder =  orderApiRequest.createOrderWithoutLogin(orderCreateModelRequest);
        Assert.assertEquals(SC_INTERNAL_SERVER_ERROR, responseOfOrder.statusCode());
    }
}
