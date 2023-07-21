package com.prgrms.repository.wallet;

import com.prgrms.exception.NotUpdateException;
import com.prgrms.model.customer.Customer;
import com.prgrms.model.customer.Name;
import com.prgrms.model.voucher.Voucher;
import com.prgrms.model.voucher.VoucherCreator;
import com.prgrms.model.voucher.VoucherType;
import com.prgrms.model.voucher.Vouchers;
import com.prgrms.model.voucher.discount.Discount;
import com.prgrms.model.voucher.discount.DiscountCreator;
import com.prgrms.model.wallet.Wallet;
import com.prgrms.presentation.message.ErrorMessage;
import com.prgrms.repository.voucher.JdbcVoucherRepository;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class JdbcWalletRepository implements WalletRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final DiscountCreator discountCreator;
    private final VoucherCreator voucherCreator;

    public JdbcWalletRepository(NamedParameterJdbcTemplate jdbcTemplate,
            DiscountCreator discountCreator, VoucherCreator voucherCreator) {
        this.jdbcTemplate = jdbcTemplate;
        this.discountCreator = discountCreator;
        this.voucherCreator = voucherCreator;
    }

    private Map<String, Object> toParamMap(Wallet wallet) {
        return new HashMap<>() {{
            put("wallet_id", wallet.getWalletId());
            put("customer_id", wallet.getCustomerId());
            put("voucher_id", wallet.getVoucherId());
            put("deleted", wallet.isDeleted());
        }};
    }

    @Override
    public Wallet insert(Wallet wallet) {
        int update = jdbcTemplate.update(INSERT_WALLET,
                toParamMap(wallet));

        if (update != 1) {
            throw new NotUpdateException(ErrorMessage.NOT_UPDATE.getMessage());
        }

        return wallet;
    }

    @Override
    public Optional<Wallet> findById(int walletId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(FIND_BY_WALLET_ID,
                    Collections.singletonMap("walletId", walletId),
                    getWalletRowMapper()));
        } catch (EmptyResultDataAccessException e) {
            logger.debug("데이터를 찾을 수 없습니다.", e);
            return Optional.empty();
        }
    }

    @Override
    public List<Wallet> findAllWallet() {
        List<Wallet> wallet = jdbcTemplate.query(SELECT_ALL_WALLETS,
                getWalletRowMapper());
        return wallet;
    }

    @Override
    public List<Customer> findAllCustomersByVoucher(int voucherId) {
        List<Customer> customers = jdbcTemplate.query(FIND_ALL_WALLET_BY_VOUCHER_ID,
                Collections.singletonMap("voucherId", voucherId),
                getCustomerRowMapper());
        return customers;
    }

    @Override
    public Vouchers findAllVouchersByCustomer(int customerId) {
        List<Voucher> vouchers = jdbcTemplate.query(FIND_ALL_WALLET_BY_CUSTOMER_ID,
                Collections.singletonMap("customerId", customerId),
                getVoucherRowMapper());
        return new Vouchers(vouchers);
    }

    @Override
    public Wallet deleteWithVoucherIdAndCustomerId(int voucherId, int customerId) {

        Wallet wallet = jdbcTemplate.queryForObject(
                SELECT_WALLET_WITH_VOUCHER_ID_AND_CUSTOMER_ID,
                new MapSqlParameterSource("customerId", customerId)
                        .addValue("voucherId", voucherId),
                getWalletRowMapper());

        if (wallet != null) {
            int update = jdbcTemplate.update(
                    UPDATE_WALLET_DELETED_TRUE,
                    new MapSqlParameterSource("customerId", customerId)
                            .addValue("voucherId", voucherId)
            );

            if (update != 1) {
                throw new NotUpdateException(ErrorMessage.NOT_UPDATE.getMessage());
            }
        }
        wallet.markAsDeleted();

        return wallet;
    }


    public RowMapper<Wallet> getWalletRowMapper() {
        return (resultSet, i) -> {
            int voucherId = resultSet.getInt("voucher_id");
            int walletId = resultSet.getInt("wallet_id");
            int customerId = resultSet.getInt("customer_id");
            return new Wallet(walletId, customerId, voucherId);
        };
    }

    public RowMapper<Customer> getCustomerRowMapper() {
        return (resultSet, i) -> {
            int customerId = resultSet.getInt("customer_id");
            Name customerName = new Name(resultSet.getString("name"));
            String email = resultSet.getString("email");
            LocalDateTime lastLoginAt = resultSet.getTimestamp("last_login_at") != null ?
                    resultSet.getTimestamp("last_login_at").toLocalDateTime() : null;
            LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
            return new Customer(customerId, customerName, email, lastLoginAt, createdAt);
        };
    }

    public RowMapper<Voucher> getVoucherRowMapper() {
        return (resultSet, i) -> {
            int voucherId = resultSet.getInt("voucher_id");
            VoucherType voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
            double discountValue = resultSet.getDouble("discount");
            Discount discount = discountCreator.createDiscount(voucherType, discountValue);
            return voucherCreator.createVoucher(voucherId, voucherType, discount);
        };
    }

    private final String SELECT_WALLET_WITH_VOUCHER_ID_AND_CUSTOMER_ID
            =
            "SELECT * "
                    + "FROM wallets "
                    + "WHERE customer_id = :customerId "
                    + "AND voucher_id = :voucherId";


    private final String UPDATE_WALLET_DELETED_TRUE
            =
            "UPDATE wallets "
                    + "SET deleted = true "
                    + "WHERE customer_id = :customerId AND voucher_id = :voucherId";

    private final String FIND_ALL_WALLET_BY_CUSTOMER_ID
            =
            "SELECT v.voucher_id, voucher_type, discount "
                    + "FROM wallets w "
                    + "INNER JOIN customers c "
                    + "ON w.customer_id = c.customer_id "
                    + "INNER JOIN vouchers v "
                    + "ON w.voucher_id = v. voucher_id "
                    + "WHERE w.customer_id = :customerId "
                    + "AND w.deleted = false";

    private final String FIND_ALL_WALLET_BY_VOUCHER_ID
            =
            "SELECT c.customer_id, name, email, last_login_at, created_at "
                    + "FROM vouchers v "
                    + "INNER JOIN wallets w on v.voucher_id = w.voucher_id "
                    + "INNER JOIN customers c ON w.customer_id = c.customer_id  "
                    + "WHERE v.voucher_id = :voucherId "
                    + "AND w.deleted = false";

    private final String FIND_BY_WALLET_ID
            =
            "select wallet_id,customer_id,voucher_id, deleted "
                    + "from wallets "
                    + "WHERE wallet_id = :walletId";

    private final String SELECT_ALL_WALLETS
            =
            "select wallet_id,customer_id,voucher_id, deleted "
                    + " from wallets";

    private final String INSERT_WALLET
            =
            "INSERT INTO wallets(wallet_id,customer_id,voucher_id,deleted) "
                    + "VALUES (:wallet_id,:customer_id,:voucher_id,:deleted)";


}
