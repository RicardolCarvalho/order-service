package store.order;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "orders")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class OrderModel {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(name = "date", nullable = false)
  private LocalDateTime  date;

  @Column(name = "id_account", nullable = false)
  private String idAccount;

  @Column(name = "total", nullable = false)
  private Double total;

  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
  private List<OrderItemModel> items = new ArrayList<>();

  public OrderModel(Order order) {
    this.id = order.id();
    this.date = order.date() != null ? LocalDateTime.parse(order.date()) : LocalDateTime.now();
    this.idAccount = order.idAccount();
    this.total = order.total();
  }

  public Order to() {
    return Order.builder()
        .id(this.id)
        .date(this.date != null ? this.date.toString() : null)
        .idAccount(this.idAccount)
        .total(this.total)
        .build();
  }
}
