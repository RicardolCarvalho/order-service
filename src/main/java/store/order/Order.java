package store.order;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

@Builder @Data @Accessors(fluent = true, chain = true)
public class Order {
    String id;
    String date;
    String idAccount;
    Double total;
}