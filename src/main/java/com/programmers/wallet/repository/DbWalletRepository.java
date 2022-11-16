package com.programmers.wallet.repository;

import com.programmers.customer.Customer;
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

import static com.programmers.message.ErrorMessage.DB_ERROR_LOG;
import static com.programmers.message.ErrorMessage.INSERT_ERROR;
import static com.programmers.wallet.repository.sql.WalletSql.*;

@Repository
public class DbWalletRepository implements WalletRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final Logger log = LoggerFactory.getLogger(DbWalletRepository.class);
    private final CustomerRowMapper customerRowMapper;
    private final VoucherRowMapper voucherRowMapper;

    public DbWalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        this.customerRowMapper = new CustomerRowMapper();
        this.voucherRowMapper = new VoucherRowMapper();
    }

    @Override
    public Customer assignVoucher(Customer customer, Voucher voucher) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("customerId", customer.getCustomerId().toString().getBytes());
        paramMap.put("voucherId", voucher.getVoucherId().toString().getBytes());
        paramMap.put("assignAt", LocalDateTime.now());

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
            return jdbcTemplate.query(FIND_VOUCHERS_WITH_CUSTOMER_ID,
                    Collections.singletonMap("customerId", customerId.toString().getBytes()),
                    voucherRowMapper);

        } catch (DataAccessException e) {
            log.error(DB_ERROR_LOG.getMessage(), e);
            return List.of();
        }
    }

    @Override
    public Optional<Customer> findCustomerByVoucherId(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_CUSTOMER_WITH_VOUCHER_ID,
                    Collections.singletonMap("voucher_id", voucherId.toString().getBytes())
                    , customerRowMapper));
        } catch (DataAccessException e) {
            log.error(DB_ERROR_LOG.getMessage(), e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteCustomerVoucher(UUID customerId, UUID voucherId) {
        try {
            jdbcTemplate.update(DELETE_CUSTOMER_VOUCHER,
                    Collections.singletonMap("voucherId",
                            voucherId.toString().getBytes()));
        } catch (DataAccessException e) {
            log.error(DB_ERROR_LOG.getMessage(), e);
        }

    }
}
