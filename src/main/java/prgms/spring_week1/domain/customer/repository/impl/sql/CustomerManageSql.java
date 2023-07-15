package prgms.spring_week1.domain.customer.repository.impl.sql;

public class CustomerManageSql {
    public static final String insertNewCustomerSQL = "INSERT INTO customers(customer_id,email, name) VALUES (UUID_TO_BIN(:customerId),:email,:name)";
    public static final String findAllCustomerSQL = "select * from customers";
    public static final String findByEmailSQL = "select * from customers WHERE email = :email";
    public static final String updateCustomerInfoSQL = "UPDATE customers SET email = :afterUpdateEmail WHERE email = :beforeUpdateEmail";
    public static final String deleteByEmailSQL = "DELETE FROM customers WHERE email = :email";
    public static final String deleteAllSQL = "DELETE FROM customers";
}
