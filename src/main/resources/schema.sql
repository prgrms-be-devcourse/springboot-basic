DROP TABLE IF EXISTS customers CASCADE;

create table customers (
                           customer_id     BINARY(16)  PRIMARY KEY,
                           name            VARCHAR(50) NOT NULL,
                           customer_grade  VARCHAR(50) NOT NULL
);

INSERT INTO customers(customer_id, name, customer_grade) VALUES (UUID_TO_BIN('4fab4016-f315-42f0-8870-f2fd04f2995f'), 'black', 'BLACK_LIST');
INSERT INTO customers(customer_id, name, customer_grade) VALUES (UUID_TO_BIN('715ddb14-6f1e-4118-aa3d-fbf577513d22'), 'gray', 'BLACK_LIST');
INSERT INTO customers(customer_id, name, customer_grade) VALUES (UUID_TO_BIN('94aef515-b5de-432a-a793-abc2aeec3405'), 'white', 'NORMAL');
INSERT INTO customers(customer_id, name, customer_grade) VALUES (UUID_TO_BIN('f0a8642b-ad72-443d-ada7-782b5e93f40b'), 'yellow', 'NORMAL');