package yandex.scooter.tests;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;
import yandex.courier.Courier;
import yandex.courier.CourierClient;
import yandex.courier.CourierAuthentication;

public class LoginCourierTests {

    Courier courier;
    CourierClient courierClient;
    CourierAuthentication courierAuthentication;
    CourierAuthentication courierAuthenticationCorrect;

    @Before
    public void setUp() {
        courier = Courier.getRandomCourier();
        courierClient = new CourierClient();
        courierClient.createCourier(courier).statusCode(201);
    }

    @After
    public void tearDown() {

        courierAuthenticationCorrect = CourierAuthentication.getCredentials(courier);
        int courierId = courierClient.loginCourier(courierAuthenticationCorrect).extract().path("id");
        courierClient.deleteCourier(courierId).statusCode(200);
    }


    @Test
    @DisplayName("Login courier with required fields - Success")
    @Description("Успешная авторизация курьера при передаче всех обязательных полей")
    public void testLoginСourierWithRequiredFieldsSuccess() {

        courierAuthentication = CourierAuthentication.getCredentials(courier);
        courierClient.loginCourier(courierAuthentication)
                .statusCode(200) //запрос возвращает правильный код ответа - 200
                .body("id", notNullValue()); //успешный запрос возвращает id != null
    }


    @Test
    @DisplayName("Login courier without login - Failure")
    @Description("Неуспешная авторизация курьером при отсутствии поля login")
    public void testLoginСourierWithoutLoginFailure() {

        courierAuthentication = CourierAuthentication.getCredentialsWithoutLogin(courier);
        String message = courierClient.loginCourier(courierAuthentication)
                .statusCode(400) //запрос возвращает код ответа - 400
                .extract().path("message");

        assertEquals("Некорректное сообщение об ошибке", "Недостаточно данных для входа", message); //неуспешный запрос возвращает корректное сообщение
    }


    @Test
    @DisplayName("Login courier without password - Failure")
    @Description("Неуспешная авторизация курьером при отсутствии поля password")
    public void testLoginСourierWithoutPasswordFailure() {

        courierAuthentication = CourierAuthentication.getCredentialsWithoutPassword(courier);
        String message = courierClient.loginCourier(courierAuthentication)
                .statusCode(400) //По документации запрос должен возвращать 400
                .extract().path("message");

        assertEquals("Некорректное сообщение об ошибке", "Недостаточно данных для входа", message);
    }


    @Test
    @DisplayName("Login courier with wrong login - Failure")
    @Description("Неуспешная авторизация курьером с некорректным значением login")
    public void testLoginСourierWithWrongLoginFailure() {

        courierAuthentication = CourierAuthentication.getCredentialsWithWrongLogin(courier);
        String message = courierClient.loginCourier(courierAuthentication)
                .statusCode(404) //запрос возвращает код ответа - 404
                .extract().path("message");

        assertEquals("Некорректное сообщение об ошибке", "Учетная запись не найдена", message); //неуспешный запрос возвращает корректное сообщение
    }


    @Test
    @DisplayName("Login courier with wrong password - Failure")
    @Description("Неуспешная авторизация курьером с некорректным значением password")
    public void testLoginСourierWithWrongPasswordFailure() {

        courierAuthentication = CourierAuthentication.getCredentialsWithWrongPassword(courier);
        String message = courierClient.loginCourier(courierAuthentication)
                .statusCode(404) //запрос возвращает код ответа - 404
                .extract().path("message");

        assertEquals("Некорректное сообщение об ошибке", "Учетная запись не найдена", message);
    }


    @Test
    @DisplayName("Login nonexistent courier - Failure")
    @Description("Авторизация несуществующим курьером невозможна")
    public void testLoginNonexistentCourierFailure() {

        courierAuthentication = CourierAuthentication.getCredentialsNonexistentCourier(courier);
        String message = courierClient.loginCourier(courierAuthentication)
                .statusCode(404)
                .extract().path("message");

        assertEquals("Некорректное сообщение об ошибке", "Учетная запись не найдена", message);
    }
}