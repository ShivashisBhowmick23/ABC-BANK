-- Insert initial customers
INSERT INTO customer (cust_name, verification_documents, cust_mail) VALUES ('John Doe', TRUE, 'john.doe@example.com');
INSERT INTO customer (cust_name, verification_documents, cust_mail) VALUES ('Jane Smith', TRUE, 'jane.smith@example.com');
INSERT INTO customer (cust_name, verification_documents, cust_mail) VALUES ('Bob Johnson', TRUE, 'bob.johnson@example.com');
INSERT INTO customer (cust_name, verification_documents, cust_mail) VALUES ('Mamata Banerjee', FALSE, 'khomota.mamata@example.com');
INSERT INTO customer (cust_name, verification_documents, cust_mail) VALUES ('Modi Chudi', FALSE, 'namo.chudi@example.com');

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

-- Insert mock data into the Transfer table
INSERT INTO transfer (transfer_id, from_account_id, to_account_id, amount, transfer_type, transfer_date) VALUES
(1, 678912345, 567891234, 150.75, 'BANK', '2024-01-01'),
(2, 123456789, 234567891, 200.50, 'UPI', '2024-01-02'),
(3, 234567891, 567891234, 300.25, 'BANK', '2024-01-03'),
(4, 345678912, 456789123, 400.00, 'BANK', '2024-01-04'),
(5, 678912345, 345678912, 500.10, 'BANK', '2024-01-05'),
(6, 678912345, 123456789, 600.75, 'UPI', '2024-01-06'),
(7, 123456789, 456789123, 700.20, 'UPI', '2024-01-07'),
(8, 456789123, 123456789, 800.50, 'UPI', '2024-01-08'),
(9, 123456789, 456789123, 900.00, 'UPI', '2024-01-09'),
(10, 456789123, 345678912, 1000.90, 'BANK', '2024-01-10');