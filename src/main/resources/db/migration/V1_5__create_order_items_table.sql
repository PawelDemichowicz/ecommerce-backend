CREATE TABLE order_items (
    id                  SERIAL           NOT NULL,
    order_id            INT              NOT NULL,
    product_id          INT              NOT NULL,
    product_name        VARCHAR(255)     NOT NULL,
    product_description TEXT             NOT NULL,
    product_price       DECIMAL(10,2)    NOT NULL,
    total_price         DECIMAL(10,2)    NOT NULL,
    quantity            INT              NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_order
        FOREIGN KEY (order_id)
            REFERENCES orders (id)
);