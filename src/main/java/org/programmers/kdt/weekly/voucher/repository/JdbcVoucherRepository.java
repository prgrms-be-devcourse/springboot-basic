package org.programmers.kdt.weekly.voucher.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.programmers.kdt.weekly.utils.UtilFunction;
import org.programmers.kdt.weekly.voucher.model.Voucher;
import org.programmers.kdt.weekly.voucher.VoucherDto;
import org.programmers.kdt.weekly.voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Primary
@Repository
public class JdbcVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(
        JdbcVoucherRepository.class);

    @Value("${voucher.insert}")
    private String insertSql;

    @Value("${voucher.update_value}")
    private String updateValueSql;

    @Value("${voucher.selectAll}")
    private String selectSql;

    @Value("${voucher.selectById}")
    private String selectByIdSql;

    @Value("${voucher.deleteById}")
    private String deleteByIdSql;

    @Value("${voucher.deleteAll}")
    private String deleteSql;

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    private final RowMapper<Voucher> voucherRowMapper = (rs, rowMapper) -> {
        var voucherId = UtilFunction.toUUID(rs.getBytes("voucher_id"));
        var voucherType = VoucherType.valueOf(rs.getString("type"));
        var value = Integer.parseInt(rs.getString("value"));
        var createdAt = rs.getTimestamp("created_at").toLocalDateTime();
        var voucher = voucherType.create(new VoucherDto(voucherId, value, createdAt)).get();

        return voucher;
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

        var update = jdbcTemplate.update(insertSql, toParamMap(voucher));
        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }

        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher) {
        var update = jdbcTemplate.update(updateValueSql, toParamMap(voucher));

        if (update != 1) {
            throw new RuntimeException("Nothing was updated");
        }

        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        try {
            return jdbcTemplate.query(selectSql, voucherRowMapper);
        } catch (EmptyResultDataAccessException e) {
            logger.error("voucher findAll empty result ", e);

            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(selectByIdSql,
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                voucherRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("voucher findById empty Result ", e);

            return Optional.empty();
        }
    }

    @Override
    public void deleteById(UUID voucherId) {
        try {
            jdbcTemplate.update(deleteByIdSql,
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()));
        } catch (EmptyResultDataAccessException e) {
            logger.error("voucher deleteById empty result ", e);
        }
    }

    @Override
    public void deleteAll() {
        try {
            jdbcTemplate.update(deleteSql, Collections.emptyMap());
        } catch (EmptyResultDataAccessException e) {
            logger.error("voucher deleteAll empty result", e);
        }
    }
}