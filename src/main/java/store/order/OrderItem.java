package store.order;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Data @Builder @Accessors(fluent = true, chain = true)
public class OrderItem {
  String id;
  String productId;
  Integer quantity;
  Double total;
}