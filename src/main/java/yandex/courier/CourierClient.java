package yandex.courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import yandex.ScooterApiClient;

public class CourierClient extends ScooterApiClient {

    private final String ROOT = "/api/v1/courier";
    private final String LOGIN = ROOT + "/login";
    private final String DELETE = ROOT + "/{id}";

    @Step("Создание курьера")
    public ValidatableResponse createCourier(Courier courier) {

        return getSpec()
                .body(courier)
                .when()
                .post(ROOT)
                .then().log().all()
                .assertThat();
    }


    @Step("Авторизация курьера")
    public ValidatableResponse loginCourier(CourierAuthentication courierAuthentication) {

        return getSpec()
                .body(courierAuthentication)
                .when()
                .post(LOGIN)
                .then().log().all()
                .assertThat();
    }

    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(int courierId) {

        return getSpec()
                .pathParams("id", courierId)
                .when()
                .delete(DELETE)
                .then().log().all()
                .assertThat();
    }
}