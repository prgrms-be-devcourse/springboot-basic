package org.prgrms.vouchermanager;

import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
public class JdbcCustomerRepository {
    private final String SELECT_BY_NAME_SQL = "select * from customers WHERE name = ? OR name = ?";
    public List<String> findNames(String name, String name2) {
        List<String> names = new ArrayList<>();
        try (
                var connection = DriverManager.getConnection("jdbc:mysql://localhost/order_mgmt", "root", "root");
                var statement = connection.prepareStatement(SELECT_BY_NAME_SQL);
        ) {
            statement.setString(1, name);
            statement.setString(2, name2);
            log.info("statement -> {}", statement);
            try (var resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    var customerName = resultSet.getString("name");
                    var customerId = UUID.nameUUIDFromBytes(resultSet.getBytes("customer_id"));
                    var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
                    names.add(customerName);
                }
            }
        } catch (SQLException throwable) {
            log.error("Got error while closing connection", throwable);
        }

        return names;
    }
    public static void main(String[] args) {
        List<String> names = new JdbcCustomerRepository().findNames("tester01", "tester02");
        names.forEach(n -> log.info("name : {}", n));
    }
}
