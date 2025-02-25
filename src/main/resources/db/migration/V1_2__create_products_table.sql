CREATE TABLE products (
    id             SERIAL           NOT NULL,
    name           VARCHAR(255)     NOT NULL,
    description    TEXT             NOT NULL,
    price          DECIMAL(10,2)    NOT NULL,
    stock          INT              NOT NULL,
    PRIMARY KEY (id)
);