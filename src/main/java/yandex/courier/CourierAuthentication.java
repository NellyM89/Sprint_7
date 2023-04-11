package yandex.courier;

import io.qameta.allure.Step;
import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;

@Data
public class CourierAuthentication {

    private String login;
    private String password;

    public CourierAuthentication(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Step("Создание данных для авторизации курьера")
    public static CourierAuthentication getCredentials(Courier courier){
        return new CourierAuthentication(
                courier.getLogin(),
                courier.getPassword()
        );
    }

    @Step("Создание данных для авторизации курьера без обязательного поля (логина)")
    public static CourierAuthentication getCredentialsWithoutLogin(Courier courier) {
        return new CourierAuthentication(
                null,
                courier.getPassword()
        );
    }

    @Step("Создание данных для авторизации курьера без обязательного поля (пароля)")
    public static CourierAuthentication getCredentialsWithoutPassword(Courier courier) {
        return new CourierAuthentication(
                courier.getLogin(),
                null
        );
    }

    @Step("Создание данных для авторизации курьера с некорректным логином")
    public static CourierAuthentication getCredentialsWithWrongLogin(Courier courier) {
        return new CourierAuthentication(
                RandomStringUtils.randomAlphanumeric(12),
                courier.getPassword()
        );
    }

    @Step("Создание данных для авторизации курьера с некорректным паролем")
    public static CourierAuthentication getCredentialsWithWrongPassword(Courier courier) {
        return new CourierAuthentication(
                courier.getLogin(),
                RandomStringUtils.randomAlphanumeric(12)
        );
    }

    @Step("Создание данных для авторизации несуществующего курьера")
    public static CourierAuthentication getCredentialsNonexistentCourier(Courier courier) {
        return new CourierAuthentication(
                RandomStringUtils.randomAlphanumeric(15),
                RandomStringUtils.randomAlphanumeric(15)
        );
    }
}