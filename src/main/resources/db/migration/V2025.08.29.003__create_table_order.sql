CREATE TABLE order_item (
  id          varchar(36) PRIMARY KEY,
  id_order    varchar(36) NOT NULL REFERENCES orders(id) ON DELETE CASCADE,
  id_product  varchar(36) NOT NULL,
  quantity    integer NOT NULL,
  total       decimal(12,2) NOT NULL
);
