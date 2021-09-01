package org.prgrms.kdt.voucher;

import org.prgrms.kdt.Utility;
import org.prgrms.kdt.customer.Customer;
import org.prgrms.kdt.customer.CustomerNamedJdbcRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.*;

@Repository
@Primary
@Profile({"dev", "staging", "production", "default"})
public class JdbcVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(CustomerNamedJdbcRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final String INSERT_SQL = "INSERT INTO vouchers(voucher_id, voucher_type, discount) " +
            "VALUES (UUID_TO_BIN(:voucherId), :voucherType, :discount)";
    private final String SELECT_SQL = "SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private final String UPDATE_SQL = "UPDATE vouchers SET discount = :discount " +
            "WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private final String DELETE_SQL = "DELETE FROM vouchers";

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = Utility.toUUID(resultSet.getBytes("voucher_id"));
        String voucherType = resultSet.getString("voucher_type");
        int discount = resultSet.getInt("discount");

        if (voucherType.equals(VoucherType.FixedAmountVoucher.name())) {
            return new FixedAmountVoucher(voucherId, discount);
        } else if (voucherType.equals(VoucherType.PercentDiscountVoucher.name())) {
            return new PercentDiscountVoucher(voucherId, discount);
        } else {
            return null;
        }
    };

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("voucherType", voucher.getType().name());
            put("discount", voucher.getDiscount());
        }};
    }

    @Override
    public Voucher insert(Voucher voucher) {
        var update = jdbcTemplate.update(INSERT_SQL, toParamMap(voucher));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("SELECT * FROM vouchers", voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject(
                            SELECT_SQL,
                            Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                            voucherRowMapper
                    ));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public Voucher update(Voucher voucher) {
        var update = jdbcTemplate.update(UPDATE_SQL, toParamMap(voucher));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return voucher;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_SQL, Collections.emptyMap());
    }

    @Override
    public Map<UUID, Voucher> getStorage() {
        return null;
    }
}
