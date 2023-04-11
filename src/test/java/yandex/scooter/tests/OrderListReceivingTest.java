package yandex.scooter.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.Test;
import yandex.order.OrderList;
import yandex.order.OrderClient;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListReceivingTest {

    OrderList orderList;
    OrderClient orderClient;

    @Before
    public void setUp() {
        orderClient = new OrderClient();
    }

    @Test
    @DisplayName("Get list of orders without parameters - Success")
    @Description("Успешное получение списка заказов без параметров в запросе")
    public void testGetListOfOrdersWithoutParametersSuccess() {

        orderList = OrderList.getDataForListOfOrdersWithoutParameters();
        orderClient.getListOfOrders(orderList)
                .statusCode(200) //запрос возвращает правильный код ответа - 200
                .body("orders", notNullValue()); //успешный запрос возвращает orders - список заказов
    }
}