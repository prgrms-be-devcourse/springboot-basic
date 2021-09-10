package com.programmers.voucher.repository.customer;

import com.programmers.voucher.entity.customer.Customer;
import com.programmers.voucher.entity.voucher.Voucher;
import com.programmers.voucher.repository.CustomerQuery;
import com.programmers.voucher.repository.VoucherQuery;
import com.programmers.voucher.repository.voucher.JdbcVoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Repository
public class JdbcCustomerRepository implements CustomerRepository {

    private static final Logger log = LoggerFactory.getLogger(JdbcCustomerRepository.class);
    private final CustomerQuery customerQuery;
    private final VoucherQuery voucherQuery;
    private final JdbcTemplate jdbcTemplate;

    public JdbcCustomerRepository(CustomerQuery customerQuery, VoucherQuery voucherQuery, JdbcTemplate jdbcTemplate) {
        this.customerQuery = customerQuery;
        this.voucherQuery = voucherQuery;
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void loadCustomers() {
        log.debug("Called JdbcCustomerRepository's loadCustomers method.");
    }

    @Override
    public void persistCustomers() {
        log.debug("Called JdbcCustomerRepository's persistCustomers method.");
    }

    @Override
    public Customer save(Customer customer) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(c -> {
            PreparedStatement statement = c.prepareStatement(customerQuery.getCreate(), Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, customer.getUsername());
            statement.setString(2, customer.getAlias());
            statement.setBoolean(3, customer.isBlacklisted());
            return statement;
        }, keyHolder);
        customer.registerId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        log.debug("Saved customer({}) to repository.", customer);
        return customer;
    }

    @Override
    public Optional<Customer> findById(long id) {
        log.debug("Find customer by id: {}", id);
        try {
            Customer customer = jdbcTemplate.queryForObject(customerQuery.getSelect().getById(), customerRowMapper, id);
            if (customer == null) {
                log.error("Queried customer returned as null by row mapper.");
                return Optional.empty();
            }
            List<Voucher> vouchers = jdbcTemplate.query(voucherQuery.getSelect().getByCustomer(), JdbcVoucherRepository.voucherRowMapper, id);
            vouchers.stream().map(Voucher::getId).forEach(customer.getVouchers()::add);
            return Optional.of(customer);
        } catch (IncorrectResultSizeDataAccessException ex) {
            log.warn("Customer does not exist or not unique customer.");
            return Optional.empty();
        } catch (DataAccessException ex) {
            log.warn("Query failed. Check DB connection status.");
            return Optional.empty();
        }
    }

    @Override
    public Optional<Customer> findByVoucher(long voucherId) {
        log.debug("Find customer by voucher: {}", voucherId);
        Customer customer;
        try {
            customer = jdbcTemplate.queryForObject(customerQuery.getSelect().getByVoucher(), customerRowMapper, voucherId);
        } catch (EmptyResultDataAccessException ex) {
            customer = null;
        }

        return Optional.ofNullable(customer);
    }

    @Override
    public List<Customer> listAll() {
        log.debug("Find all customers");
        return jdbcTemplate.query(customerQuery.getSelect().getAll(), customerRowMapper);
    }

    public static final RowMapper<Customer> customerRowMapper = (rs, rowNum) -> new Customer(
            rs.getLong("customer_id"),
            rs.getString("username"),
            rs.getString("alias"),
            rs.getBoolean("blacklisted"),
            rs.getTimestamp("created_at").toLocalDateTime().toLocalDate()
    );
}
