package com.pgms.part1.domain.customer.repository;

import com.pgms.part1.domain.customer.entity.Customer;
import com.pgms.part1.domain.customer.entity.CustomerBuilder;
import com.pgms.part1.domain.wallet.entity.Wallet;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Primary
@Repository
public class CustomerJdbcRepository implements CustomerRepository{

    private final JdbcTemplate jdbcTemplate;

    public CustomerJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Customer mapCustomer(ResultSet resultSet) throws SQLException {
        return new CustomerBuilder().id(resultSet.getLong("id"))
                .name(resultSet.getString("name"))
                .email(resultSet.getString("email"))
                .isBlocked(resultSet.getBoolean("is_blocked")).build();
    }

    @Override
    public List<Customer> listBlockedCustomers() {
        String listBlockedCustomersSql = "SELECT * FROM CUSTOMERS WHERE is_blocked = true";
        return jdbcTemplate.query(listBlockedCustomersSql, (resultSet, i) ->
                mapCustomer(resultSet));
    }

    @Override
    public List<Customer> listCustomers() {
        String listCustomersSql = "SELECT * FROM CUSTOMERS";
        return jdbcTemplate.query(listCustomersSql, (resultSet, i) ->
                mapCustomer(resultSet));
    }

    @Override
    public void addCustomer(Customer customer) {
        String addCustomerSql = "INSERT INTO CUSTOMERS(id, name, email, is_blocked) values (?, ?, ?, ?)";
        jdbcTemplate.update(addCustomerSql, customer.getId(), customer.getName(), customer.getEmail(), customer.getBlocked());
    }

    @Override
    public void updateCustomerName(Customer customer, String name) {
        String updateCustomerNameSql = "UPDATE CUSTOMERS SET name = ? where name = ?";
        jdbcTemplate.update(updateCustomerNameSql, name, customer.getName());
    }

    @Override
    public void deleteCustomer(Customer customer) {
        String deleteCustomerSql = "DELETE FROM CUSTOMERS WHERE id = ?";
        jdbcTemplate.update(deleteCustomerSql, customer.getId());
    }

    @Override
    public void findCustomerByWallet(Wallet wallet) {

    }
}
