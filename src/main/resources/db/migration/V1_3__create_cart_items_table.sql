CREATE TABLE cart_items (
    id             SERIAL           NOT NULL,
    user_id        INT              NOT NULL,
    product_id     INT              NOT NULL,
    quantity       INT              NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (user_id, product_id),
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES users (id),
    CONSTRAINT fk_product
        FOREIGN KEY (product_id)
            REFERENCES products (id)
);