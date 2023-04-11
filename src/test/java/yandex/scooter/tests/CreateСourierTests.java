package yandex.scooter.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import yandex.courier.Courier;
import yandex.courier.CourierClient;
import yandex.courier.CourierAuthentication;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertTrue;

public class CreateСourierTests {

    Courier courier;
    CourierClient courierClient;
    private boolean isOk;

    @Before
    public void setUp() {
        courierClient = new CourierClient();
    }
    @After
    public void tearDown() {
        if (isOk){
            CourierAuthentication courierAuthentication = CourierAuthentication.getCredentials(courier);
            int courierId = courierClient.loginCourier(courierAuthentication).extract().path("id");
            courierClient.deleteCourier(courierId).statusCode(200);
        }
    }


    @Test
    @DisplayName("Creating courier - Success")
    @Description("Создание курьера - позитивное")
    public void testCreatingСourierSuccess() {

        courier = Courier.getRandomCourier();
        isOk = courierClient.createCourier(courier)
                .statusCode(201) //запрос возвращает правильный код ответа - 201
                .extract().path("ok");

        assertTrue(isOk); //успешный запрос возвращает ok=true
    }


    @Test
    @DisplayName("Creating courier with required fields - Success")
    @Description("Создание курьера при передаче в запросе всех обязательных полей - позитивное")
    public void testCreatingСourierWithRequiredFieldsSuccess() {

        courier = Courier.getCourierWithRequiredFields();
        isOk = courierClient.createCourier(courier)
                .statusCode(201)
                .extract().path("ok");

        assertTrue(isOk);
    }


    @Test
    @DisplayName("Creating courier without login - Failure")
    @Description("Создание курьера при отсутствии в запросе поля login - негативное")
    public void testCreatingСourierWithoutLoginFailure() {

        courier = Courier.getCourierWithoutLogin();
        courierClient.createCourier(courier)
                .statusCode(400) //запрос возвращает правильный код ответа - 400
                .body("message", equalTo("Недостаточно данных для создания учетной записи")); //неуспешный запрос возвращает корректное сообщение
    }


    @Test
    @DisplayName("Creating courier without password - Failure")
    @Description("Создание курьера при отсутствии в запросе поля password - негативное")
    public void testCreatingСourierWithoutPasswordFailure() {

        courier = Courier.getCourierWithoutPassword();
        courierClient.createCourier(courier)
                .statusCode(400) //запрос возвращает правильный код ответа - 400
                .body("message", equalTo("Недостаточно данных для создания учетной записи")); //неуспешный запрос возвращает корректное сообщение
    }


    @Test
    @DisplayName("Creating courier with duplicate login - Failure")
    @Description("Создание курьера с login как у существующего курьера - негативное")
    public void testCreatingСourierWithDuplicateLoginFailure() {

        courier = Courier.getRandomCourier();
        isOk = courierClient.createCourier(courier)
                .statusCode(201)
                .extract().path("ok");
        assertTrue(isOk);

        Courier duplicateCourier = Courier.getCourierDuplicateLogin(courier);
        courierClient.createCourier(duplicateCourier)
                .statusCode(409) //запрос возвращает правильный код ответа - 409
                .body("message", equalTo("Этот логин уже используется. Попробуйте другой.")); //неуспешный запрос возвращает корректное сообщение
    }
}