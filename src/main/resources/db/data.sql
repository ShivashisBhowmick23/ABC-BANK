-- Insert initial customers
INSERT INTO customer (cust_name, verification_documents, cust_mail) VALUES ('John Doe', TRUE, 'john.doe@example.com');
INSERT INTO customer (cust_name, verification_documents, cust_mail) VALUES ('Jane Smith', TRUE, 'jane.smith@example.com');
INSERT INTO customer (cust_name, verification_documents, cust_mail) VALUES ('Bob Johnson', TRUE, 'bob.johnson@example.com');

-- Insert initial accounts with fixed 9-digit IDs for the example
INSERT INTO account (account_id, account_type, balance, cust_id) VALUES (123456789, 'SAVINGS', 1000.0, 1);
INSERT INTO account (account_id, account_type, balance, cust_id) VALUES (234567891, 'CHECKING', 1500.0, 1);
INSERT INTO account (account_id, account_type, balance, cust_id) VALUES (345678912, 'SAVINGS', 2000.0, 2);
INSERT INTO account (account_id, account_type, balance, cust_id) VALUES (456789123, 'CHECKING', 2500.0, 2);
INSERT INTO account (account_id, account_type, balance, cust_id) VALUES (567891234, 'SAVINGS', 3000.0, 3);
INSERT INTO account (account_id, account_type, balance, cust_id) VALUES (678912345, 'CHECKING', 3500.0, 3);

-- Insert initial transactions referencing the correct account IDs
INSERT INTO transaction (account_id, amount, transaction_type, transaction_date) VALUES (123456789, 100.0, 'DEPOSIT', '2024-01-01');
INSERT INTO transaction (account_id, amount, transaction_type, transaction_date) VALUES (234567891, 200.0, 'WITHDRAWAL', '2024-01-02');
INSERT INTO transaction (account_id, amount, transaction_type, transaction_date) VALUES (345678912, 300.0, 'DEPOSIT', '2024-01-03');
INSERT INTO transaction (account_id, amount, transaction_type, transaction_date) VALUES (456789123, 400.0, 'WITHDRAWAL', '2024-01-04');
INSERT INTO transaction (account_id, amount, transaction_type, transaction_date) VALUES (567891234, 500.0, 'DEPOSIT', '2024-01-05');
INSERT INTO transaction (account_id, amount, transaction_type, transaction_date) VALUES (678912345, 600.0, 'WITHDRAWAL', '2024-01-06');
