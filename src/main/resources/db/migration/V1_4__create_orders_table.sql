CREATE TABLE orders (
    id             SERIAL           NOT NULL,
    user_id        INT              NOT NULL,
    order_date     TIMESTAMP        NOT NULL,
    status         VARCHAR(50)      NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_user
        FOREIGN KEY (user_id)
            REFERENCES users (id)
);