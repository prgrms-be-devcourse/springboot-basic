package com.programmers.wallet.repository;

import com.programmers.customer.Customer;
import com.programmers.voucher.voucher.Voucher;
import com.programmers.voucher.voucher.VoucherFactory;
import com.programmers.voucher.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.time.LocalDateTime;
import java.util.*;

import static com.programmers.wallet.repository.sql.WalletSql.*;

@Repository
public class DbWalletRepository implements WalletRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final Logger log = LoggerFactory.getLogger(DbWalletRepository.class);

    public DbWalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final static RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        String customerName = resultSet.getString("name");
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        String email = resultSet.getString("email");
        LocalDateTime createAt = resultSet.getTimestamp("create_at").toLocalDateTime();
        LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;

        return new Customer(customerId, customerName, email, lastLoginAt, createAt);
    };

    private final static RowMapper<Voucher> voucherRowMapper = (resultSet, i) ->{
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        long voucherValue = resultSet.getLong("voucher_value");
        String voucherType = resultSet.getString("voucher_type").substring(0, 1);

        VoucherType validateVoucherType = VoucherType.getValidateVoucherType(voucherType);
        return VoucherFactory.createVoucher(voucherId, validateVoucherType, voucherValue);
    };

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    @Override
    public Customer assignVoucher(Customer customer, Voucher voucher) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("customerId", customer.getCustomerId().toString().getBytes());
        paramMap.put("voucherId", voucher.getVoucherId().toString().getBytes());
        paramMap.put("assignAt", LocalDateTime.now());

        int count = jdbcTemplate.update(INSERT_WALLET, paramMap);
        if (count != 1) {
            log.error("repository error");
            throw new RuntimeException("바우처 할당 실패");
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
            log.error("repository 예외 발생", e);
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
            log.error("repository 예외 발생", e);
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
            log.error("repository 예외 발생", e);
        }

    }
}
