package org.prgrms.kdt.repository.voucher;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.kdt.model.voucher.Voucher;
import org.prgrms.kdt.model.voucher.VoucherType;
import org.prgrms.kdt.util.UuidUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
@Profile({"default"})
public class JdbcVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        var voucherId = UuidUtils.toUUID(resultSet.getBytes("voucher_id"));
        var discount = resultSet.getLong("discount");
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        var voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
        return new Voucher(voucherId, discount, createdAt, voucherType);

    };

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final String SELECT_ALL = "SELECT * FROM voucher";
    private final String SELECT_BY_ID = "SELECT * FROM voucher WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private final String INSERT = """
        INSERT INTO voucher(voucher_id, voucher_type, discount, created_at)
        VALUES (UUID_TO_BIN(:voucherId), :voucherType, :discount, :createdAt)""";
    private final String UPDATE_TYPE = "UPDATE voucher SET voucher_type = :voucherType, discount = :discount where voucher_id = UUID_TO_BIN(:voucherId)";
    private String DELETE_ALL = "DELETE FROM voucher";

    private String SELECT_BY_CUSTOMER_ID = """
        SELECT * FROM voucher
        LEFT OUTER JOIN wallet
        ON voucher.voucher_id = wallet.voucher_id
        WHERE wallet.customer_id = UUID_TO_BIN(:customerId)""";


    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        var vouchers = jdbcTemplate.query(
            SELECT_BY_ID,
            Map.of("voucherId", voucherId.toString().getBytes()),
            voucherRowMapper
        );
        return Optional.ofNullable(DataAccessUtils.singleResult(vouchers));
    }

    @Override
    public Voucher insert(Voucher voucher) {
        var update = jdbcTemplate.update(
            INSERT,
            toParamMap(voucher));

        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }
        return voucher;
    }

    @Override
    public Voucher updateType(Voucher voucher) {
        var update = jdbcTemplate.update(
            UPDATE_TYPE,
            toParamMap(voucher));
        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAllVoucher() {
        return jdbcTemplate.query(SELECT_ALL, voucherRowMapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.getJdbcTemplate().update(DELETE_ALL);

    }

    @Override
    public List<Voucher> findByCustomerId(UUID customerId) {
        return jdbcTemplate.query(
            SELECT_BY_CUSTOMER_ID,
            Map.of("customerId", customerId.toString().getBytes()),
            voucherRowMapper);
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        var paramMap = new HashMap<String, Object>();
        paramMap.put("voucherId", voucher.getVoucherId().toString().getBytes());
        paramMap.put("discount", voucher.getDiscount());
        paramMap.put("createdAt", Timestamp.valueOf(voucher.getCreatedAt()));
        paramMap.put("voucherType", voucher.getVoucherType().name());
        return paramMap;
    }


}

