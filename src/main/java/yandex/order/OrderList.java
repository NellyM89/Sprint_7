package yandex.order;

import io.qameta.allure.Step;
import lombok.Data;

@Data
public class OrderList {

    private Integer courierId;
    private String nearestStation;
    private Integer limit;
    private Integer page;

    public OrderList(Integer courierId, String nearestStation, Integer limit, Integer page) {
        this.courierId = courierId;
        this.nearestStation = nearestStation;
        this.limit = limit;
        this.page = page;
    }

    @Step("Формирование данных для запроса списка заказов без параметров")
    public static OrderList getDataForListOfOrdersWithoutParameters() {
        return new OrderList(
                null,
                null,
                null,
                null
        );
    }
}
