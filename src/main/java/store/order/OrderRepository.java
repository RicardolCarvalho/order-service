package store.order;

import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends CrudRepository<OrderModel, String> {

  List<OrderModel> findAllByIdAccount(String idAccount);

  Optional<OrderModel> findByIdAndIdAccount(String id, String idAccount);
}
