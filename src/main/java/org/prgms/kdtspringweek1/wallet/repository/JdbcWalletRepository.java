package org.prgms.kdtspringweek1.wallet.repository;

import org.prgms.kdtspringweek1.customer.entity.Customer;
import org.prgms.kdtspringweek1.exception.DataException;
import org.prgms.kdtspringweek1.exception.DataExceptionCode;
import org.prgms.kdtspringweek1.voucher.entity.FixedAmountVoucher;
import org.prgms.kdtspringweek1.voucher.entity.PercentDiscountVoucher;
import org.prgms.kdtspringweek1.voucher.entity.VoucherType;
import org.prgms.kdtspringweek1.wallet.entity.Wallet;
import org.prgms.kdtspringweek1.voucher.entity.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static org.prgms.kdtspringweek1.JdbcUtils.toUUID;

@Repository
@Profile({"default", "test"})
public class JdbcWalletRepository implements WalletRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcWalletRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcWalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        long discountValue = resultSet.getLong("discount_value");
        VoucherType voucherType = VoucherType.getVoucherTypeByName(resultSet.getString("voucher_type"));

        switch (voucherType) {
            case FIXED_AMOUNT -> {
                return FixedAmountVoucher.createWithIdAndAmount(voucherId, discountValue);
            }
            case PERCENT_DISCOUNT -> {
                return PercentDiscountVoucher.createWithIdAndPercent(voucherId, discountValue);
            }
        }
        throw new RuntimeException("행 매핑에 실패했습니다.");
    };


    private static final RowMapper<Customer> customerRowMapper = (resultSet, i) -> {
        UUID customerId = toUUID(resultSet.getBytes("customer_id"));
        String name = resultSet.getString("name");
        boolean isBlackCustomer = resultSet.getBoolean("is_black_customer");
        return Customer.createWithIdAndNameAndIsBlackCustomer(customerId, name, isBlackCustomer);
    };

    private static Map<String, Object> toParamMap(Wallet wallet) {
        return new HashMap<>() {{
            put("walletId", wallet.getWalletId().toString().getBytes());
            put("voucherId", wallet.getVoucherId().toString().getBytes());
            put("customerId", wallet.getCustomerId().toString().getBytes());
        }};
    }

    @Override
    public Wallet save(Wallet wallet) {
        int isInserted = jdbcTemplate.update("INSERT INTO wallets(wallet_id, voucher_id, customer_id) VALUES (UUID_TO_BIN(:walletId), UUID_TO_BIN(:voucherId), UUID_TO_BIN(:customerId))",
                toParamMap(wallet));
        if (isInserted != 1) {
            logger.error(DataExceptionCode.FAIL_TO_INSERT.getMessage());
            throw new DataException(DataExceptionCode.FAIL_TO_INSERT);
        }

        return wallet;
    }

    @Override
    public List<Voucher> findAllVouchersByCustomerId(UUID customerId) {
        String sql = "SELECT * FROM vouchers WHERE voucher_id in (SELECT voucher_id from wallets WHERE customer_id = UUID_TO_BIN(:customerId))";

        return jdbcTemplate.query(sql,
                Collections.singletonMap("customerId", customerId.toString().getBytes()),
                voucherRowMapper);
    }

    @Override
    public void deleteByVoucherIdAndCustomerId(UUID voucherId, UUID customerId) {
        int isUpdated = jdbcTemplate.update("DELETE FROM wallets WHERE voucher_id = UUID_TO_BIN(:voucherId) and customer_id = UUID_TO_BIN(:customerId)",
                Map.of(
                        "voucherId", voucherId.toString().getBytes(),
                        "customerId", customerId.toString().getBytes()
                )
        );

        if (isUpdated != 1) {
            logger.error(DataExceptionCode.FAIL_TO_DELETE.getMessage());
            throw new DataException(DataExceptionCode.FAIL_TO_DELETE);
        }
    }

    @Override
    public List<Customer> findAllCustomersByVoucherId(UUID voucherId) {
        String sql = "SELECT * FROM customers WHERE customer_id in (SELECT customer_id from wallets WHERE voucher_id = UUID_TO_BIN(:voucherId))";

        return jdbcTemplate.query(sql,
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                customerRowMapper);
    }
}
