package yandex.scooter.tests;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import yandex.order.CreateOrder;

import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class CreateOrderTests {

    private final String firstName;
    private final String lastName;
    private final String address;
    private final String metroStation;
    private final String phone;
    private final int rentTime;
    private final String deliveryDate;
    private final String comment;
    private final String[] color;

    public CreateOrderTests(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters(name = "Test data: {8}")
    public static Object[][] getTestData() {
        return new Object[][]{
                {"Naruto", "Uzumaki", "Kanoha, 142 apt.", "1", "+7 800 355 35 35", 5, "2023-04-10", "AFAP!", new String[]{"BLACK"}},
                {"Naruto", "Uzumaki", "Kanoha, 142 apt.", "1", "+7 800 355 35 35", 5, "2023-04-10", "AFAP!", new String[]{"GREY"}},
                {"Naruto", "Uzumaki", "Kanoha, 142 apt.", "1", "+7 800 355 35 35", 5, "2023-04-10", "AFAP!", new String[]{"BLACK", "GREY"}},
                {"Naruto", "Uzumaki", "Kanoha, 142 apt.", "1", "+7 800 355 35 35", 5, "2023-04-10", "AFAP!", null},
                {"Naruto", "Uzumaki", "Kanoha, 142 apt.", "1", "+7 800 355 35 35", 5, "2023-04-10", "AFAP!", new String[]{}}
        };
    }

    @Test
    @DisplayName("Create order with various color scooter")
    @Description("Создание заказов с различными цветами самоката-позитивное")
    public void testCreateOrderWithVariousColorScooter() {

        CreateOrder createOrder = new CreateOrder(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);

        int orderTrack = createOrder.createOrder();

        assertNotNull(orderTrack); //Проверяем, что созданный заказ имеет уникальный номер (track) и не равен null.
    }

}