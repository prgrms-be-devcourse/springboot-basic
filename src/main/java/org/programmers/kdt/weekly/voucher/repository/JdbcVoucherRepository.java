package org.programmers.kdt.weekly.voucher.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.programmers.kdt.weekly.utils.UtilFunction;
import org.programmers.kdt.weekly.voucher.VoucherDto;
import org.programmers.kdt.weekly.voucher.model.Voucher;
import org.programmers.kdt.weekly.voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(
        JdbcVoucherRepository.class);

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<Voucher> voucherRowMapper = (rs, rowMapper) -> {
        var voucherId = UtilFunction.toUUID(rs.getBytes("voucher_id"));
        var voucherType = VoucherType.valueOf(rs.getString("type"));
        var value = Integer.parseInt(rs.getString("value"));
        var createdAt = rs.getTimestamp("created_at").toLocalDateTime();

        return voucherType.create(new VoucherDto(voucherId, value, createdAt));
    };

    private Map<String, Object> toParamMap(Voucher voucher) {
        HashMap<String, Object> voucherMap = new HashMap<>();
        String[] voucherData = voucher.serializeVoucher().split(",");
        voucherMap.put("voucherId", voucherData[0]);
        voucherMap.put("voucherType", voucherData[1]);
        voucherMap.put("value", voucherData[2]);
        voucherMap.put("createdAt", voucherData[3]);

        return voucherMap;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        String insertSql = "INSERT INTO voucher(voucher_id, type, value, created_at) " +
            "VALUES (UNHEX(REPLACE(:voucherId, '-', '')), :voucherType, :value, :createdAt)";
        var update = jdbcTemplate.update(insertSql, toParamMap(voucher));

        if (update == 0) {
            throw new RuntimeException("Nothing was inserted");
        }

        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher) {
        String updateValueSql = "UPDATE voucher SET value = :value " +
            "WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))";
        var update = jdbcTemplate.update(updateValueSql, toParamMap(voucher));

        if (update == 0) {
            throw new RuntimeException("Nothing was updated");
        }

        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        String selectSql = "SELECT * FROM voucher";

        return jdbcTemplate.query(selectSql, voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String selectByIdSql = "SELECT * FROM voucher "
            + "WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))";

        try {
            var voucher = jdbcTemplate.queryForObject(selectByIdSql,
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                voucherRowMapper);

            return Optional.ofNullable(voucher);
        } catch (EmptyResultDataAccessException e) {
            logger.error("voucher findById empty Result ", e);

            return Optional.empty();
        }
    }

    @Override
    public void deleteById(UUID voucherId) {
        String deleteByIdSql = "DELETE FROM voucher " +
            "WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))";

        try {
            jdbcTemplate.update(deleteByIdSql,
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            logger.error("voucher deleteById empty result ", e);
        }
    }

    @Override
    public void deleteAll() {
        String deleteSql = "DELETE FROM voucher";
        jdbcTemplate.update(deleteSql, Collections.emptyMap());
    }
}