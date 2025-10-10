package store.order;

import java.util.List;
import java.time.LocalDateTime;
import store.product.ProductOut;

public class OrderParser {

    public static Order to(OrderIn in) {
        return in == null ? null :
            Order.builder()
                .id(null)
                .date(LocalDateTime.now().toString())
                .idAccount(null) // será definido no service
                .total(0.0)
                .build();
    }

    public static OrderOut to(Order order) {
        return order == null ? null :
            OrderOut.builder()
                .id(order.id())
                .date(order.date())
                .items(null) // será preenchido no service quando necessário
                .total(order.total())
                .build();
    }

    public static List<OrderOut> to(List<Order> orders) {
        return orders == null ? null :
            orders.stream().map(OrderParser::to).toList();
    }

    public static OrderOut toOut(OrderModel om, List<ProductOut> products) {
        if (om == null) return null;

        var itemsOut = om.getItems().stream().map(im -> {
            ProductOut product = products.stream()
                .filter(p -> p.id().equals(im.getIdProduct()))
                .findFirst().orElse(null);

            return OrderItemOut.builder()
                .id(im.getId())
                .product(product)
                .quantity(im.getQuantity())
                .total(im.getTotal())
                .build();
        }).toList();

        return OrderOut.builder()
            .id(om.getId())
            .date(om.getDate().toString())
            .items(itemsOut)
            .total(om.getTotal())
            .build();
    }

    public static OrderModel toModel(OrderIn in, String idAccount) {
        OrderModel om = new OrderModel();
        om.setId(null);
        om.setDate(LocalDateTime.now());
        om.setIdAccount(idAccount);
        om.setTotal(0.0);
        return om;
    }
}
