package store.order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import store.product.ProductController;
import store.product.ProductOut;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ProductController productController;

    @Transactional
    public Order create(OrderIn in, String idAccount) {
        if (in == null || in.items() == null || in.items().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Items are mandatory");
        }

        List<ProductOut> products = new ArrayList<>();
        in.items().forEach(it -> {
            ProductOut p = productController.findById(it.idProduct()).getBody();
            if (p == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not found: " + it.idProduct());
            }
            products.add(p);
        });

        OrderModel om = OrderParser.toModel(in, idAccount);

        double orderTotal = 0.0;
        for (int i = 0; i < in.items().size(); i++) {
            var inItem = in.items().get(i);
            var product = products.get(i);

            double total = product.price() * inItem.quantity();

            OrderItemModel im = new OrderItemModel();
            im.setOrder(om);
            im.setIdProduct(inItem.idProduct());
            im.setQuantity(inItem.quantity());
            im.setTotal(total);

            om.getItems().add(im);
            orderTotal += total;
        }
        om.setTotal(orderTotal);

        OrderModel saved = orderRepository.save(om);
        return saved.to();
    }

    public List<Order> findAll(String idAccount) {
        return StreamSupport.stream(
            orderRepository.findAllByIdAccount(idAccount).spliterator(), false)
            .map(OrderModel::to)
            .toList();
    }

    public Order findById(String id, String idAccount) {
        return orderRepository.findByIdAndIdAccount(id, idAccount)
            .map(OrderModel::to)
            .orElse(null);
    }

    @Transactional
    public OrderOut createWithDetails(OrderIn in, String idAccount) {
        if (in == null || in.items() == null || in.items().isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Items are mandatory");
        }

        List<ProductOut> products = new ArrayList<>();
        in.items().forEach(it -> {
            ProductOut p = productController.findById(it.idProduct()).getBody();
            if (p == null) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Product not found: " + it.idProduct());
            }
            products.add(p);
        });

        OrderModel om = OrderParser.toModel(in, idAccount);

        double orderTotal = 0.0;
        for (int i = 0; i < in.items().size(); i++) {
            var inItem = in.items().get(i);
            var product = products.get(i);

            double total = product.price() * inItem.quantity();

            OrderItemModel im = new OrderItemModel();
            im.setOrder(om);
            im.setIdProduct(inItem.idProduct());
            im.setQuantity(inItem.quantity());
            im.setTotal(total);

            om.getItems().add(im);
            orderTotal += total;
        }
        om.setTotal(orderTotal);

        OrderModel saved = orderRepository.save(om);
        return OrderParser.toOut(saved, products);
    }

    public List<OrderOut> findAllWithDetails(String idAccount) {
        var list = orderRepository.findAllByIdAccount(idAccount);
        return list.stream()
            .map(om -> OrderOut.builder()
                .id(om.getId())
                .date(om.getDate().toString())
                .items(null) // lista resumida
                .total(om.getTotal())
                .build())
            .toList();
    }

    public OrderOut findByIdWithDetails(String id, String idAccount) {
        OrderModel om = orderRepository.findByIdAndIdAccount(id, idAccount)
            .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Order not found"));

        // carrega os produtos dos itens desse pedido
        List<ProductOut> products = om.getItems().stream()
            .map(it -> productController.findById(it.getIdProduct()).getBody())
            .toList();

        return OrderParser.toOut(om, products);
    }
}
