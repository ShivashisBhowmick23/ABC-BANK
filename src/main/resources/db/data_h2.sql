-- Insert initial customers
INSERT INTO customer (cust_name, verification_documents, cust_mail) VALUES ('Peter Parker', TRUE, 'spiderman@justice-league.com');
INSERT INTO customer (cust_name, verification_documents, cust_mail) VALUES ('Selina Kyle', TRUE, 'catwoman@justice-league.com');
INSERT INTO customer (cust_name, verification_documents, cust_mail) VALUES ('Barry Allen', TRUE, 'the-flash@justice-league.com');
INSERT INTO customer (cust_name, verification_documents, cust_mail) VALUES ('Steve Trevor', TRUE, 'hawkeye@justice-league.com');
INSERT INTO customer (cust_name, verification_documents, cust_mail) VALUES ('Scott Summers', TRUE, 'cyclops@justice-league.com');
INSERT INTO customer (cust_name, verification_documents, cust_mail) VALUES ('Raven Darkhölme', TRUE, 'black-canary@justice-league.com');
INSERT INTO customer (cust_name, verification_documents, cust_mail) VALUES ('Dinah Laurel Lance', TRUE, 'huntress@justice-league.com');
INSERT INTO customer (cust_name, verification_documents, cust_mail) VALUES ('Tony Stark', TRUE, 'ironman@justice-league.com');
INSERT INTO customer (cust_name, verification_documents, cust_mail) VALUES ('Natasha Romanoff', TRUE, 'black-widow@justice-league.com');
INSERT INTO customer (cust_name, verification_documents, cust_mail) VALUES ('Steve Rogers', TRUE, 'captain-america@justice-league.com');
INSERT INTO customer (cust_name, verification_documents, cust_mail) VALUES ('Thor Odinson', TRUE, 'thor@justice-league.com');
INSERT INTO customer (cust_name, verification_documents, cust_mail) VALUES ('Bruce Banner', TRUE, 'hulk@justice-league.com');
INSERT INTO customer (cust_name, verification_documents, cust_mail) VALUES ('Namor the Sub-Mariner', TRUE, 'namor-the-sub-mariner@justice-league.com');
INSERT INTO customer (cust_name, verification_documents, cust_mail) VALUES ('Reed Richards', TRUE, 'mister-fantastic@justice-league.com');
INSERT INTO customer (cust_name, verification_documents, cust_mail) VALUES ('Susan Storm', TRUE, 'invisible-woman@justice-league.com');
INSERT INTO customer (cust_name, verification_documents, cust_mail) VALUES ('Henry Peter Gyrich', TRUE, 'the-flash@justice-league.com');

-- Insert initial accounts with fixed 9-digit IDs for the example
INSERT INTO account (account_id, account_type, balance, cust_id) VALUES (123456789, 'SAVINGS', 1000.0, 1);
INSERT INTO account (account_id, account_type, balance, cust_id) VALUES (234567891, 'CHECKING', 1500.0, 1);
INSERT INTO account (account_id, account_type, balance, cust_id) VALUES (345678912, 'SAVINGS', 2000.0, 2);
INSERT INTO account (account_id, account_type, balance, cust_id) VALUES (456789123, 'CHECKING', 2500.0, 2);
INSERT INTO account (account_id, account_type, balance, cust_id) VALUES (567891234, 'SAVINGS', 3000.0, 5);
INSERT INTO account (account_id, account_type, balance, cust_id) VALUES (678912345, 'CHECKING', 3500.0, 4);
INSERT INTO account (account_id, account_type, balance, cust_id) VALUES (888888888, 'CHECKING', 3500.0, 7);
INSERT INTO account (account_id, account_type, balance, cust_id) VALUES (777777777, 'SAVINGS', 3500.0, 8);
INSERT INTO account (account_id, account_type, balance, cust_id) VALUES (666666666, 'SAVINGS', 3500.0, 9);
INSERT INTO account (account_id, account_type, balance, cust_id) VALUES (575757575, 'CHECKING', 3500.0, 10);

-- Insert initial transactions referencing the correct account IDs
INSERT INTO transaction (account_id, amount, transaction_type, transaction_date) VALUES (123456789, 100.0, 'DEPOSIT', '2024-01-01');
INSERT INTO transaction (account_id, amount, transaction_type, transaction_date) VALUES (234567891, 200.0, 'WITHDRAWAL', '2024-01-02');
INSERT INTO transaction (account_id, amount, transaction_type, transaction_date) VALUES (345678912, 300.0, 'DEPOSIT', '2024-01-03');
INSERT INTO transaction (account_id, amount, transaction_type, transaction_date) VALUES (456789123, 400.0, 'WITHDRAWAL', '2024-01-04');
INSERT INTO transaction (account_id, amount, transaction_type, transaction_date) VALUES (567891234, 500.0, 'DEPOSIT', '2024-01-05');
INSERT INTO transaction (account_id, amount, transaction_type, transaction_date) VALUES (678912345, 600.0, 'WITHDRAWAL', '2024-01-06');

-- Insert mock data into the Transfer table
INSERT INTO transfer (from_account_id, to_account_id, amount, transfer_type, transfer_date) VALUES
( 678912345, 567891234, 150, 'BANK', '2024-01-01'),
( 123456789, 234567891, 200, 'UPI', '2024-01-02'),
( 234567891, 567891234, 300, 'BANK', '2024-01-03'),
( 345678912, 456789123, 400, 'BANK', '2024-01-04'),
( 678912345, 345678912, 500, 'BANK', '2024-01-05'),
( 678912345, 123456789, 600, 'UPI', '2024-01-06'),
( 123456789, 456789123, 700, 'UPI', '2024-01-07'),
( 456789123, 123456789, 800, 'UPI', '2024-01-08'),
( 123456789, 456789123, 900, 'UPI', '2024-01-09'),
( 456789123, 345678912, 1000, 'BANK', '2024-01-10');