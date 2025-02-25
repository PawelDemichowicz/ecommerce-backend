CREATE TABLE users (
    id          SERIAL       NOT NULL,
    username    VARCHAR(100) NOT NULL,
    email       VARCHAR(255) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    role        VARCHAR(50)  NOT NULL,
    PRIMARY KEY (id),
    UNIQUE (username, email)
);