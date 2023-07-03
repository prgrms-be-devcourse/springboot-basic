package kr.co.programmers.springbootbasic.customer.repository.impl;

import kr.co.programmers.springbootbasic.customer.domain.Customer;
import kr.co.programmers.springbootbasic.customer.domain.CustomerStatus;
import kr.co.programmers.springbootbasic.customer.domain.impl.JdbcCustomer;
import kr.co.programmers.springbootbasic.customer.repository.CustomerRepository;
import kr.co.programmers.springbootbasic.util.ApplicationUtils;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer save(Customer customer) {
        UUID customerId = customer.getId();
        String customerName = customer.getName();
        int statusId = customer.getStatus().getStatusId();
        UUID walletId = customer.getWalletId();

        jdbcTemplate.update(
                "INSERT INTO customer (id, name, status_id, wallet_id) VALUES (UUID_TO_BIN(?), ?, ?, UUID_TO_BIN(?))",
                customerId,
                customerName,
                statusId,
                walletId);

        return customer;
    }


    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            Customer customer = jdbcTemplate.queryForObject("SELECT * FROM customer WHERE id = UUID_TO_BIN(?)",
                    custmerRowMapper,
                    customerId.toString().getBytes());

            return Optional.ofNullable(customer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query("SELECT * FROM customer", custmerRowMapper);
    }

    @Override
    public Customer update(Customer customer) {
        UUID customerId = customer.getId();
        String customerName = customer.getName();
        int statusId = customer.getStatus().getStatusId();

        jdbcTemplate.update("UPDATE customer SET name = ?, status_id = ? WHERE id = UUID_TO_BIN(?)",
                customerName,
                statusId,
                customerId);

        return customer;
    }

    @Override
    public void deleteById(UUID customerId) {
        jdbcTemplate.update("DELETE FROM customer WHERE id = UUID_TO_BIN(?)", customerId);
    }

    private final RowMapper<Customer> custmerRowMapper = ((rs, rowNum) -> {
        UUID customerId = ApplicationUtils.toUUID(rs.getBytes("id"));
        String customerName = rs.getString("name");
        CustomerStatus status = CustomerStatus.resolveId(rs.getInt("status_id"));
        UUID walletId = ApplicationUtils.toUUID(rs.getBytes("wallet_id"));

        return new JdbcCustomer(customerId, customerName, status, walletId);
    });
}
