package org.prgrms.kdt.voucher.repository;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.StringJoiner;
import java.util.UUID;
import org.prgrms.kdt.exception.NotFoundException;
import org.prgrms.kdt.utils.UuidUtils;
import org.prgrms.kdt.voucher.controller.VoucherSearch;
import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.model.VoucherType;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
@Profile({"default"})
public class VoucherJdbcRepository implements VoucherRepository {

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
    private String DELETE_BY_ID = "DELETE FROM voucher WHERE voucher_id = UUID_TO_BIN(:voucherId)";
    private String SELECT_BY_CUSTOMER_ID = """
        SELECT * FROM voucher
        LEFT OUTER JOIN wallet
        ON voucher.voucher_id = wallet.voucher_id
        WHERE wallet.customer_id = UUID_TO_BIN(:customerId)""";


    public VoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
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
            throw new NotFoundException("Nothing was inserted");
        }
        return voucher;
    }

    @Override
    public Voucher updateType(Voucher voucher) {
        var update = jdbcTemplate.update(
            UPDATE_TYPE,
            toParamMap(voucher));
        if (update != 1) {
            throw new NotFoundException("Nothing was updated");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAllVoucher() {
        return jdbcTemplate.query(SELECT_ALL, voucherRowMapper);
    }

    @Override
    public List<Voucher> findFilteredVouchers(VoucherSearch voucherSearch) {
        StringJoiner where = new StringJoiner(" AND ", " WHERE ", "").setEmptyValue("");
        Map<String, Object> params = new HashMap<>();
        if(voucherSearch.getVoucherType() != null) {
            params.put("voucherType", voucherSearch.getVoucherType());
            where.add("voucher_type = :voucherType");
        }
        if(voucherSearch.getFrom() != null) {
            params.put("from", voucherSearch.getFrom());
            where.add("created_at >= :from");
        }
        if(voucherSearch.getTo() != null) {
            params.put("to", voucherSearch.getTo());
            where.add("created_at <= :to");
        }
        return jdbcTemplate.query(SELECT_ALL + where, params, voucherRowMapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.getJdbcTemplate().update(DELETE_ALL);
    }

    @Override
    public void deleteById(UUID voucherId) {
        var update = jdbcTemplate.update(
            DELETE_BY_ID,
            Map.of("voucherId", voucherId.toString().getBytes()));
        if (update != 1) {
            throw new NotFoundException("Nothing was deleted");
        }

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

