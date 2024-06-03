CREATE TABLE IF NOT EXISTS customer (
    cust_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cust_name VARCHAR(255) NOT NULL,
    verification_documents BOOLEAN NOT NULL,
    cust_mail VARCHAR(255) NOT NULL
);

CREATE TABLE IF NOT EXISTS account (
    account_id BIGINT PRIMARY KEY,
    account_type VARCHAR(255) NOT NULL,
    balance DOUBLE NOT NULL,
    cust_id BIGINT,
    FOREIGN KEY (cust_id) REFERENCES customer(cust_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS transaction (
    transaction_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    account_id BIGINT NOT NULL,
    amount DOUBLE NOT NULL,
    transaction_type VARCHAR(255) NOT NULL,
    transaction_date DATE NOT NULL,
    FOREIGN KEY (account_id) REFERENCES account(account_id)
);
