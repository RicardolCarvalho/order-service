CREATE TABLE orders (
  id           varchar(36) PRIMARY KEY,
  date         timestamptz NOT NULL,
  id_account   varchar(36) NOT NULL,
  total        decimal(12,2) NOT NULL
);
