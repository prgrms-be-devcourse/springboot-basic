package org.prgrms.kdt.jdbcRepository;

import org.prgrms.kdt.domain.CustomerEntity;
import org.prgrms.kdt.domain.VoucherEntity;
import org.prgrms.kdt.enumType.VoucherStatus;
import org.prgrms.kdt.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.util.*;

import static org.prgrms.kdt.jdbcRepository.CustomerJdbcRepository.toUUID;

@Repository
public class VoucherJdbcRepository implements VoucherRepository {

    private final Logger logger = LoggerFactory.getLogger(VoucherJdbcRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private HashMap<String, Object> toParamMap(VoucherEntity voucherEntity) {
        return new HashMap<>() {
            {
                put("voucherId", voucherEntity.getVoucherId().toString().getBytes());
                put("voucherType", voucherEntity.getVoucherType());
                put("discount", voucherEntity.getDiscount());
                put("createdAt", Timestamp.valueOf(voucherEntity.getCreatedAt()));
            }
        };
    }

    public static RowMapper<VoucherEntity> voucherEntityRowMapper = (resultSet, i) -> {
        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
        var voucherType = resultSet.getString("voucher_type");
        var discount = resultSet.getLong("discount");
        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        return new VoucherEntity(voucherId, voucherType, discount, createdAt);
    };

    @Override
    public VoucherEntity insert(VoucherEntity voucherEntity) {
        var update = jdbcTemplate.update("insert into voucher(voucher_id, voucher_type, discount, created_at) values(UUID_TO_BIN(:voucherId), :voucherType, :discount, :createdAt)",
                toParamMap(voucherEntity));
        if (update != 1) {
            throw new RuntimeException("Nothing was Inserted");
        }

        return voucherEntity;
    }

    @Override
    public VoucherEntity update(VoucherEntity voucherEntity) {
        var update = jdbcTemplate.update("update voucher set voucher_type = :voucherType, discount= :discount  where voucher_id = UUID_TO_BIN(:voucherId)",
                toParamMap(voucherEntity));
        if (update != 1) {
            throw new RuntimeException("Nothing was Updated");
        }
        return voucherEntity;
    }

    @Override
    public List<VoucherEntity> findAll() {
        return jdbcTemplate.query("select * from voucher", voucherEntityRowMapper);
    }

    @Override
    public Optional<VoucherEntity> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from voucher where voucher_id = UUID_TO_BIN(:voucherId)",
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    voucherEntityRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got Empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from voucher", Collections.emptyMap());
    }

    @Override
    public void deleteById(UUID voucherId) {
        var voucherIdMap = Collections.singletonMap("voucherId", voucherId.toString().getBytes());
        jdbcTemplate.update("delete from voucher where voucher_id = :voucherId", voucherIdMap);
    }
}
