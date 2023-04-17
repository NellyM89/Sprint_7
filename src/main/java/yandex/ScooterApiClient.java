package yandex;

import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ScooterApiClient {

    protected RequestSpecification getSpec() {
        return given().log().all()
                .baseUri("http://qa-scooter.praktikum-services.ru/")
                .header("Content-Type", "application/json");
    }
}