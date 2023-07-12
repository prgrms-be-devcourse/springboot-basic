package kr.co.programmers.springbootbasic.customer.repository.impl;

import kr.co.programmers.springbootbasic.customer.domain.Customer;
import kr.co.programmers.springbootbasic.customer.domain.CustomerStatus;
import kr.co.programmers.springbootbasic.customer.domain.impl.JdbcCustomer;
import kr.co.programmers.springbootbasic.customer.repository.CustomerRepository;
import kr.co.programmers.springbootbasic.util.ApplicationUtils;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("web")
public class JdbcCustomerRepository implements CustomerRepository {
    private static final String CREATE_CUSTOMER
            = "INSERT INTO customer (id, name, status_id, wallet_id) VALUES (UUID_TO_BIN(?), ?, ?, UUID_TO_BIN(?))";
    private static final String FIND_BY_CUSTOMER_ID = "SELECT * FROM customer WHERE id = UUID_TO_BIN(?)";
    private static final String FIND_ALL = "SELECT * FROM customer";
    private static final String UPDATE_CUSTOMER
            = "UPDATE customer SET status_id = ? WHERE id = UUID_TO_BIN(?)";
    private static final String DELETE_WALLET = "DELETE FROM wallet WHERE id = UUID_TO_BIN(?)";
    private static final String FIND_BY_VOUCHER_ID
            = "SELECT * FROM voucher AS v JOIN customer AS c ON v.wallet_id = c.wallet_id WHERE v.id = UUID_TO_BIN(?)";
    private static final String CREATE_WALLET = "INSERT INTO wallet (id) VALUES (UUID_TO_BIN(?))";
    private static final String DELETE_VOUCHER = "DELETE FROM voucher WHERE wallet_id = UUID_TO_BIN(?)";
    private static final String DELETE_CUSTOMER = "DELETE FROM customer WHERE id = UUID_TO_BIN(?)";
    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Customer createCustomer(Customer customer) {
        UUID customerId = customer.getId();
        String customerName = customer.getName();
        int statusId = customer.getStatus().getStatusId();
        UUID walletId = customer.getWalletId();

        jdbcTemplate.update(CREATE_CUSTOMER,
                customerId.toString().getBytes(),
                customerName,
                statusId,
                walletId.toString().getBytes());
        jdbcTemplate.update(CREATE_WALLET,
                walletId.toString().getBytes());

        return customer;
    }

    @Override
    public Optional<Customer> findByCustomerId(String customerId) {
        try {
            Customer customer = jdbcTemplate.queryForObject(FIND_BY_CUSTOMER_ID,
                    custmerRowMapper,
                    customerId);

            return Optional.ofNullable(customer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(FIND_ALL, custmerRowMapper);
    }

    @Override
    public Customer update(Customer customer) {
        UUID customerId = customer.getId();
        int statusId = customer.getStatus().getStatusId();

        jdbcTemplate.update(UPDATE_CUSTOMER,
                statusId,
                customerId.toString().getBytes());

        return customer;
    }

    @Override
    public void deleteVoucherByWalletId(UUID walletId) {
        jdbcTemplate.update(DELETE_VOUCHER,
                walletId.toString().getBytes());
    }

    @Override
    public void deleteWalletByWalletId(UUID walletId) {
        jdbcTemplate.update(DELETE_WALLET,
                walletId.toString().getBytes());
    }

    @Override
    public void deleteCustomerById(String customerId) {
        jdbcTemplate.update(DELETE_CUSTOMER,
                customerId.getBytes());
    }

    @Override
    public Optional<Customer> findByVoucherId(String voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_VOUCHER_ID,
                    customerJoinRowMapper(),
                    voucherId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private final RowMapper<Customer> custmerRowMapper = ((rs, rowNum) -> {
        UUID customerId = ApplicationUtils.toUUID(rs.getBytes("id"));
        String customerName = rs.getString("name");
        CustomerStatus status = CustomerStatus.resolveId(rs.getInt("status_id"));
        UUID walletId = ApplicationUtils.toUUID(rs.getBytes("wallet_id"));

        return new JdbcCustomer(customerId, customerName, status, walletId);
    });

    private RowMapper<Customer> customerJoinRowMapper() {
        return (rs, rowNum) -> {
            var id = ApplicationUtils.toUUID(rs.getBytes("c.id"));
            var name = rs.getString("c.name");
            var statusId = CustomerStatus.resolveId(rs.getInt("c.status_id"));
            var walletId = ApplicationUtils.toUUID(rs.getBytes("c.wallet_id"));

            return new JdbcCustomer(id, name, statusId, walletId);
        };
    }
}
