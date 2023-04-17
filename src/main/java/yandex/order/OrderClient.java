package yandex.order;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import yandex.ScooterApiClient;

public class OrderClient extends ScooterApiClient {

    private final String ROOT_ORDER = "/api/v1/orders";

    @Step("Получение списка заказов")
    public ValidatableResponse getListOfOrders(OrderList orderList) {

        return getSpec()
                .body(orderList)
                .when()
                .get(ROOT_ORDER)
                .then().log().all()
                .assertThat();
    }

}