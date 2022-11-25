package org.prgrms.springorder.domain.customer.repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.springorder.domain.voucher_wallet.model.Wallet;
import org.prgrms.springorder.domain.customer.model.Customer;
import org.prgrms.springorder.domain.customer.model.CustomerStatus;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.model.VoucherType;
import org.prgrms.springorder.domain.voucher.service.VoucherFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Profile({"dev", "test"})
public class CustomerJdbcRepository implements CustomerRepository {

    private static final Logger logger = LoggerFactory.getLogger(CustomerJdbcRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<Customer> customerRowMapper = (rs, rowNum) -> {

        String customerId = rs.getString("customer_id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        CustomerStatus customerStatus = CustomerStatus.of(rs.getString("customer_status"));

        LocalDateTime createdAt = rs.getTimestamp("created_at")
            != null ? rs.getTimestamp("created_at").toLocalDateTime() : null;

        LocalDateTime lastLoginAt = rs.getTimestamp("last_login_at")
            == null ? null : rs.getTimestamp("last_login_at").toLocalDateTime();

        return new Customer(UUID.fromString(customerId), name, email, lastLoginAt, createdAt,
            customerStatus);
    };

    private Map<String, Object> toParamMap(Customer customer) {
        HashMap<String, Object> paramMap = new HashMap<>();

        paramMap.put("customerId", customer.getCustomerId().toString());
        paramMap.put("name", customer.getName());
        paramMap.put("email", customer.getEmail());
        paramMap.put("customerStatus", customer.getCustomerStatus().name());

        paramMap.put("createdAt", customer.getCreatedAt() == null ? null
            : Timestamp.valueOf(customer.getCreatedAt()));
        paramMap.put("lastLoginAt", customer.getLastLoginAt() == null ? null : Timestamp.valueOf(
            customer.getLastLoginAt()));

        return paramMap;
    }

    public CustomerJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Customer> findById(UUID customerId) {
        try {
            Customer findCustomer = jdbcTemplate.queryForObject(FIND_BY_ID,
                Collections.singletonMap("customerId", customerId.toString())
                , customerRowMapper);

            return Optional.ofNullable(findCustomer);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Customer insert(Customer customer) {
        try {
            jdbcTemplate.update(INSERT, toParamMap(customer));

            return customer;
        } catch (DataAccessException e) {
            logger.error("voucher insert error. name {},  message {}", e.getClass().getName(),
                e.getMessage());
            throw e;
        }
    }

    @Override
    public List<Customer> findAll() {
        return jdbcTemplate.query(FIND_ALL, customerRowMapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL, Collections.emptyMap());
    }

    @Override
    public Customer update(Customer customer) {
        jdbcTemplate.update(UPDATE_BY_ID, toParamMap(customer));
        return customer;
    }

    @Override
    public Optional<Wallet> findByIdWithVouchers(UUID customerId) {

        try {

            Wallet wallet = jdbcTemplate.query(FIND_BY_ID_WITH_VOUCHERS,
                Collections.singletonMap("customerId", customerId.toString()),
                rs -> {
                    Customer customer = null;
                    List<Voucher> vouchers = new ArrayList<>();

                    int row = 0;

                    while (rs.next()) {
                        if (customer == null) {
                            customer = customerRowMapper.mapRow(rs, row);
                        }

                        UUID voucherId = UUID.fromString(rs.getString("voucher_id"));
                        long amount = rs.getLong("amount");
                        VoucherType voucherType = VoucherType.of(rs.getString("voucher_type"));

                        LocalDateTime createdAt = rs.getTimestamp("created_at")
                            .toLocalDateTime();

                        Voucher voucher = VoucherFactory.toVoucher(voucherType, voucherId, amount, createdAt);

                        vouchers.add(voucher);

                        row++;
                    }

                    return new Wallet(customer, vouchers);
                }
            );

            if (wallet != null && wallet.isEmpty()) {
                return Optional.empty();
            }

            return Optional.ofNullable(wallet);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    private static final String FIND_BY_ID = "SELECT * FROM customers WHERE customer_id = :customerId";

    private static final String INSERT = "INSERT INTO customers(customer_id, name, email, last_login_at) "
        + "VALUES (:customerId, :name, :email, :lastLoginAt)";

    private static final String FIND_ALL = "SELECT * FROM customers";

    private static final String DELETE_ALL = "DELETE FROM customers";

    private static final String UPDATE_BY_ID = "UPDATE customers "
        + "SET name = :name, email = :email, customer_status = :customerStatus, last_login_at = :lastLoginAt "
        + "WHERE customer_id = :customerId ";

    private static final  String FIND_BY_ID_WITH_VOUCHERS =
        "SELECT * FROM customers c INNER JOIN vouchers v "
        + "ON c.customer_id = v.customer_id "
        + "WHERE c.customer_id = :customerId";

}
