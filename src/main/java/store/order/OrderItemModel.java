package store.order;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "order_item")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OrderItemModel {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "id_order", nullable = false)
  private OrderModel order;

  @Column(name = "id_product", nullable = false)
  private String idProduct;

  @Column(name = "quantity", nullable = false)
  private Integer quantity;

  @Column(name = "total", nullable = false)
  private Double total;
}
