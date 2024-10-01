-- Insert initial customers with different values
INSERT INTO customer (cust_name, verification_documents, cust_mail)
VALUES
('Clark Kent', TRUE, 'superman@justice-league.com'),
('Diana Prince', TRUE, 'wonder-woman@justice-league.com'),
('Arthur Curry', TRUE, 'aquaman@justice-league.com'),
('Victor Stone', TRUE, 'cyborg@justice-league.com'),
('Hal Jordan', TRUE, 'green-lantern@justice-league.com'),
('Bruce Wayne', TRUE, 'batman@justice-league.com'),
('Oliver Queen', TRUE, 'green-arrow@justice-league.com'),
('Kara Zor-El', TRUE, 'supergirl@justice-league.com'),
('Wally West', TRUE, 'kid-flash@justice-league.com'),
('Billy Batson', TRUE, 'shazam@justice-league.com'),
('Jessica Cruz', TRUE, 'green-lantern-2@justice-league.com'),
('John Stewart', TRUE, 'green-lantern-3@justice-league.com'),
('Zatanna Zatara', TRUE, 'zatanna@justice-league.com'),
('Martian Manhunter', TRUE, 'martian-manhunter@justice-league.com'),
('Barry Allen', TRUE, 'the-flash-2@justice-league.com');

-- Insert initial accounts with different 9-digit IDs
INSERT INTO account (account_id, account_type, balance, cust_id)
VALUES
(987654321, 'SAVINGS', 2000.0, 1),
(876543219, 'CHECKING', 2500.0, 2),
(765432198, 'SAVINGS', 3000.0, 3),
(654321987, 'CHECKING', 3500.0, 4),
(543219876, 'SAVINGS', 4000.0, 5),
(432198765, 'CHECKING', 4500.0, 6),
(321987654, 'CHECKING', 5000.0, 7),
(219876543, 'SAVINGS', 5500.0, 8),
(198765432, 'SAVINGS', 6000.0, 9),
(876543210, 'CHECKING', 6500.0, 10);

-- Insert initial transactions with different amounts and dates
INSERT INTO transaction (account_id, amount, transaction_type, transaction_date)
VALUES
(987654321, 150.0, 'DEPOSIT', '2024-02-01'),
(876543219, 250.0, 'WITHDRAWAL', '2024-02-02'),
(765432198, 350.0, 'DEPOSIT', '2024-02-03'),
(654321987, 450.0, 'WITHDRAWAL', '2024-02-04'),
(543219876, 550.0, 'DEPOSIT', '2024-02-05'),
(432198765, 650.0, 'WITHDRAWAL', '2024-02-06');

-- Insert mock data into the Transfer table with different values
INSERT INTO transfer (from_account_id, to_account_id, amount, transfer_type, transfer_date)
VALUES
(987654321, 876543219, 250, 'UPI', '2024-02-01'),
(765432198, 654321987, 350, 'BANK', '2024-02-02'),
(543219876, 432198765, 450, 'UPI', '2024-02-03'),
(219876543, 198765432, 550, 'BANK', '2024-02-04'),
(987654321, 765432198, 650, 'BANK', '2024-02-05'),
(876543219, 543219876, 750, 'UPI', '2024-02-06'),
(654321987, 219876543, 850, 'BANK', '2024-02-07'),
(765432198, 987654321, 950, 'UPI', '2024-02-08'),
(432198765, 876543210, 1050, 'BANK', '2024-02-09'),
(987654321, 543219876, 1150, 'UPI', '2024-02-10');