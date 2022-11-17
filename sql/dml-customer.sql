INSERT INTO customers_demo(customer_id, name, birth_date, email, created_at, black_list)
VALUES (UUID_TO_BIN(:customerId), :name, :birth, :email, :createdAt, :blackList);
SELECT *
FROM customers_demo
WHERE customer_id = UUID_TO_BIN(:customerId);
SELECT *
FROM customers_demo
WHERE email = :email;
SELECT *
FROM customers_demo
WHERE name = :name;
SELECT *
FROM customers_demo;
UPDATE customers_demo
SET name         = :name,
    email=:email,
    last_login_at=:lastLoginAt
where customer_id = UUID_TO_BIN(:customerId);
DELETE
FROM customers_demo
WHERE customer_id = UUID_TO_BIN(:customerId);
delete
FROM customers_demo;
SELECT customer_id, email, birth_date
FROM customers_demo
WHERE black_list = true;