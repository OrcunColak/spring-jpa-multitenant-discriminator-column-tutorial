CREATE TABLE person (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    age INTEGER,
    email VARCHAR(255),
    tenant_id VARCHAR(255) NOT NULL -- Discriminator column
);
