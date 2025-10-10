package store.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class OrderResource implements OrderController {

    @Autowired
    private OrderService orderService;

    @Override
    public ResponseEntity<OrderOut> create(OrderIn in) {
        String idAccount = CurrentRequest.idAccount();
        OrderOut out = orderService.createWithDetails(in, idAccount);
        return ResponseEntity.created(
            ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(out.id())
                .toUri()
        ).body(out);
    }

    @Override
    public ResponseEntity<List<OrderOut>> findAll() {
        String idAccount = CurrentRequest.idAccount();
        return ResponseEntity.ok(orderService.findAllWithDetails(idAccount));
    }

    @Override
    public ResponseEntity<OrderOut> findById(String id) {
        String idAccount = CurrentRequest.idAccount();
        return ResponseEntity.ok(orderService.findByIdWithDetails(id, idAccount));
    }
}
