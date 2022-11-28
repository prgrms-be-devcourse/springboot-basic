package org.prgrms.springorder.domain.voucher_wallet.repository;

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
import org.prgrms.springorder.domain.voucher.api.CustomerWithVoucher;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.model.VoucherType;
import org.prgrms.springorder.domain.voucher.service.VoucherFactory;
import org.prgrms.springorder.domain.voucher_wallet.model.VoucherWallet;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Profile({"dev", "test"})
public class VoucherWalletJdbcRepository implements
    VoucherWalletRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VoucherWalletJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<VoucherWallet> voucherWalletRowMapper = ((rs, rowNum) -> {

        UUID wallet_id = UUID.fromString(rs.getString("wallet_id"));
        UUID customerId = UUID.fromString(rs.getString("customer_id"));
        UUID voucherId = UUID.fromString(rs.getString("voucher_id"));

        LocalDateTime createdAt = rs.getTimestamp("created_at").toLocalDateTime();

        return new VoucherWallet(wallet_id, customerId, voucherId, createdAt);
    });


    private Map<String, Object> toParamMap(VoucherWallet voucherWallet) {
        HashMap<String, Object> paramMap = new HashMap<>();

        paramMap.put("walletId", voucherWallet.getWalletId().toString());
        paramMap.put("customerId", voucherWallet.getCustomerId().toString());
        paramMap.put("voucherId", voucherWallet.getVoucherId().toString());
        paramMap.put("createdAt", Timestamp.valueOf(voucherWallet.getCreatedAt()));

        return paramMap;
    }

    @Override
    public VoucherWallet insert(VoucherWallet voucherWallet) {
        jdbcTemplate.update(INSERT, toParamMap(voucherWallet));
        return voucherWallet;
    }

    @Override
    public Optional<Wallet> findAllWithCustomerAndVoucher(UUID customerId) {

        try {
            Wallet wallet = jdbcTemplate.query(FIND_ALL_BY_CUSTOMER_ID_WITH_VOUCHERS,
                Collections.singletonMap("customerId", customerId.toString()),
                rs -> {
                    Customer customer = null;
                    List<Voucher> vouchers = new ArrayList<>();

                    int row = 0;

                    while (rs.next()) {
                        if (customer == null) {
                            customer = customerRowMapper.mapRow(rs, row);
                        }

                        UUID voucherId = UUID.fromString(rs.getString("v.voucher_id"));
                        long amount = rs.getLong("amount");
                        VoucherType voucherType = VoucherType.of(rs.getString("voucher_type"));

                        LocalDateTime createdAt = rs.getTimestamp("v.created_at")
                            .toLocalDateTime();

                        Voucher voucher = VoucherFactory.toVoucher(voucherType, voucherId, amount,
                            createdAt);

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

    @Override
    public void deleteByCustomerId(UUID customerId) {
        jdbcTemplate.update(DELETE_BY_CUSTOMER_ID,
            Collections.singletonMap("customerId", customerId.toString()));
    }

    @Override
    public Optional<CustomerWithVoucher> findByVoucherIdWithCustomer(UUID voucherId) {
        try {
            CustomerWithVoucher customerWithVoucher
                = jdbcTemplate.queryForObject(FIND_BY_VOUCHER_ID_WITH_CUSTOMER,
                Collections.singletonMap("voucherId", voucherId.toString()),
                (rs, rowNum) -> {
                    UUID findVoucherId = UUID.fromString(rs.getString("v.voucher_id"));
                    long amount = rs.getLong("amount");
                    VoucherType voucherType = VoucherType.of(rs.getString("voucher_type"));

                    LocalDateTime voucherCreatedAt = rs.getTimestamp("v.created_at")
                        .toLocalDateTime();

                    UUID customerId = UUID.fromString(rs.getString("c.customer_id"));

                    String name = rs.getString("name");
                    String email = rs.getString("email");
                    CustomerStatus customerStatus = CustomerStatus.of(
                        rs.getString("customer_status"));

                    return new CustomerWithVoucher(findVoucherId, amount, voucherCreatedAt,
                        voucherType, customerId, name, email, customerStatus);
                });
            return Optional.ofNullable(customerWithVoucher);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }

    }

    @Override
    public void deleteByCustomerIdAndVoucherID(UUID customerId, UUID voucherId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("customerId", customerId.toString());
        paramMap.put("voucherId", voucherId.toString());
        jdbcTemplate.update(DELETE_BY_CUSTOMER_ID_AND_VOUCHER_ID, paramMap);
    }

    @Override
    public Optional<VoucherWallet> findByCustomerIdAndVoucherId(UUID customerId, UUID voucherId) {
        try {
            Map<String, Object> paramMap = new HashMap<>();
            paramMap.put("customerId", customerId.toString());
            paramMap.put("voucherId", voucherId.toString());

            VoucherWallet voucherWallet = jdbcTemplate.queryForObject(
                FIND_BY_CUSTOMER_ID_AND_VOUCHER_ID,
                paramMap,
                voucherWalletRowMapper);
            return Optional.ofNullable(voucherWallet);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public boolean existsByCustomerIdAndVoucherId(UUID customerId, UUID voucherId) {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("customerId", customerId.toString());
        paramMap.put("voucherId", voucherId.toString());
        return Boolean.TRUE.equals(
            jdbcTemplate.queryForObject(EXISTS_BY_CUSTOMER_ID_AND_VOUCHER_ID, paramMap,
                Boolean.class));
    }

    private final RowMapper<Customer> customerRowMapper = (rs, rowNum) -> {

        String customerId = rs.getString("c.customer_id");
        String name = rs.getString("name");
        String email = rs.getString("email");
        CustomerStatus customerStatus = CustomerStatus.of(rs.getString("customer_status"));

        LocalDateTime createdAt = rs.getTimestamp("c.created_at").toLocalDateTime();

        LocalDateTime lastLoginAt = rs.getTimestamp("last_login_at")
            == null ? null : rs.getTimestamp("last_login_at").toLocalDateTime();

        return new Customer(UUID.fromString(customerId), name, email, lastLoginAt, createdAt,
            customerStatus);
    };

    private static final String INSERT =
        "INSERT INTO voucher_wallet(wallet_id, customer_id, voucher_id, created_at)  "
            + "VALUES (:walletId, :customerId, :voucherId, :createdAt)";

    private static final String DELETE_BY_CUSTOMER_ID
        = "DELETE FROM voucher_wallet WHERE customer_id = :customerId";

    private static final String FIND_ALL_BY_CUSTOMER_ID_WITH_VOUCHERS
        = "SELECT * FROM voucher_wallet w "
        + "INNER JOIN customers c "
        + "ON w.customer_id = c.customer_id "
        + "INNER JOIN vouchers v "
        + "ON w.voucher_id = v. voucher_id "
        + "WHERE w.customer_id = :customerId";

    private static final String FIND_BY_VOUCHER_ID_WITH_CUSTOMER = "SELECT "
        + " * FROM vouchers v "
        + "INNER JOIN voucher_wallet vw on v.voucher_id = vw.voucher_id "
        + "INNER JOIN customers c ON vw.customer_id = c.customer_id  "
        + "WHERE v.voucher_id = :voucherId";

    private static final String FIND_BY_CUSTOMER_ID_AND_VOUCHER_ID
        = "SELECT * FROM voucher_wallet "
        + "WHERE customer_id = :customerId and voucher_id = :voucherId";

    private static final String DELETE_BY_CUSTOMER_ID_AND_VOUCHER_ID
        = "DELETE FROM voucher_wallet "
        + "WHERE customer_id = :customerId "
        + "AND "
        + "voucher_id = :voucherId";

    private static final String EXISTS_BY_CUSTOMER_ID_AND_VOUCHER_ID
        = "SELECT EXISTS "
        + "(SELECT 1 FROM voucher_wallet "
        + "WHERE customer_id = :customerId "
        + "AND "
        + "voucher_id = :voucherId)";

}
