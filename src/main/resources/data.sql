INSERT INTO customer (customer_id, name, email, created_at)
VALUES ('f47ac10b-58cc-4372-a567-0e02b2c3d479', 'John Doe', 'john.doe@example.com', '2023-10-24 12:00:00'),
       ('e47d2a4b-58cd-4373-a568-1e03b3c4d580', 'Jane Smith', 'jane.smith@example.com', '2023-10-24 12:05:00');


INSERT INTO voucher (voucher_id, voucher_type, percent, amount)
VALUES ('f47ac10b-58cc-4372-a567-0e02b2c3d478', 'PERCENT_DISCOUNT', 10, NULL),
       ('a35bc21d-38dc-4671-b345-7a33c4b5e579', 'PERCENT_DISCOUNT', 15, NULL),
       ('5c16f1df-4f14-4f99-b5ad-5f678f216568', 'FIXED_AMOUNT', NULL, 1000),
       ('7d14f8dd-7f41-4956-833d-6d3035c5f5e2', 'FIXED_AMOUNT', NULL, 2000);
