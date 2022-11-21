package com.programmers.wallet.repository;

import com.programmers.customer.Customer;
import com.programmers.customer.repository.sql.CustomerResultSetExtractor;
import com.programmers.customer.repository.sql.CustomerRowMapper;
import com.programmers.voucher.repository.sql.VoucherRowMapper;
import com.programmers.voucher.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.*;

import static com.programmers.customer.repository.DbCustomerRepository.CUSTOMER_ID;
import static com.programmers.message.ErrorMessage.DB_ERROR_LOG;
import static com.programmers.message.ErrorMessage.INSERT_ERROR;
import static com.programmers.voucher.repository.DbVoucherRepository.VOUCHER_ID;
import static com.programmers.wallet.repository.sql.WalletSql.*;
import static java.util.Collections.emptyMap;

@Repository
public class DbWalletRepository implements WalletRepository {
    public static final String ASSIGN_AT = "assignAt";
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final Logger log = LoggerFactory.getLogger(DbWalletRepository.class);
    private final CustomerResultSetExtractor resultSetExtractor;
    private final CustomerRowMapper customerRowMapper;
    private final VoucherRowMapper voucherRowMapper;

    public DbWalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = new CustomerRowMapper();
        this.voucherRowMapper = new VoucherRowMapper();
        this.resultSetExtractor = new CustomerResultSetExtractor(customerRowMapper, voucherRowMapper);
    }

    @Override
    public Customer assignVoucher(Customer customer, Voucher voucher) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put(CUSTOMER_ID, customer.getCustomerId().toString().getBytes());
        paramMap.put(VOUCHER_ID, voucher.getVoucherId().toString().getBytes());
        paramMap.put(ASSIGN_AT, LocalDateTime.now());

        int count = jdbcTemplate.update(INSERT_WALLET, paramMap);
        if (count != 1) {
            log.error(DB_ERROR_LOG.getMessage());
            throw new RuntimeException(INSERT_ERROR.getMessage());
        }
        return customer;
    }

    @Override
    public List<Voucher> findVouchersByCustomerId(UUID customerId) {
        try {
            return jdbcTemplate.query(
                    FIND_VOUCHERS_WITH_CUSTOMER_ID,
                    Collections.singletonMap(CUSTOMER_ID, customerId.toString().getBytes()),
                    voucherRowMapper
            );
        } catch (DataAccessException e) {
            log.error(DB_ERROR_LOG.getMessage(), e);
            throw new RuntimeException(DB_ERROR_LOG.getMessage());
        }
    }

    @Override
    public Optional<Customer> findCustomerByVoucherId(UUID voucherId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.query(
                            FIND_CUSTOMER_WITH_VOUCHER_ID,
                            Collections.singletonMap(VOUCHER_ID, voucherId.toString().getBytes()),
                            resultSetExtractor
                    )
            );
        } catch (DataAccessException e) {
            log.error(DB_ERROR_LOG.getMessage(), e);
            throw new RuntimeException(DB_ERROR_LOG.getMessage());
        }
    }

    @Override
    public void deleteCustomerVoucher(UUID customerId, UUID voucherId) {
        try {
            jdbcTemplate.update(
                    DELETE_CUSTOMER_VOUCHER,
                    Collections.singletonMap(VOUCHER_ID, voucherId.toString().getBytes())
            );
        } catch (DataAccessException e) {
            log.error(DB_ERROR_LOG.getMessage(), e);
            throw new RuntimeException(DB_ERROR_LOG.getMessage());
        }

    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_WALLET, emptyMap());
    }
}
