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

import static org.prgrms.kdt.utill.EntityUtill.toVoucherParamMap;
import static org.prgrms.kdt.utill.EntityUtill.voucherEntityRowMapper;

@Repository
public class VoucherJdbcRepository implements VoucherRepository {

    private final Logger logger = LoggerFactory.getLogger(VoucherJdbcRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final String SELECT_BY_ID_SQL = "select * from voucher where voucher_id = UUID_TO_BIN(:voucherId)";
    private final String SELECT_ALL_SQL = "select * from voucher";
    private final String INSERT_SQL = "insert into voucher(voucher_id, voucher_type, discount, created_at) values(UUID_TO_BIN(:voucherId), :voucherType, :discount, :createdAt)";
    private final String DELETE_ALL_SQL = "delete from voucher";
    private final String DELETE_BY_ID_SQL = "delete from voucher where voucher_id = UUID_TO_BIN(:voucherId)";
    private final String UPDATE_BY_ID_SQL = "update voucher set voucher_type = :voucherType, discount= :discount  where voucher_id = UUID_TO_BIN(:voucherId)";

    @Override
    public VoucherEntity insert(VoucherEntity voucherEntity) {
        var update = jdbcTemplate.update(INSERT_SQL,
                toVoucherParamMap(voucherEntity));
        if (update != 1) {
            throw new RuntimeException("Nothing was Inserted");
        }

        return voucherEntity;
    }

    @Override
    public VoucherEntity update(VoucherEntity voucherEntity) {
        var update = jdbcTemplate.update(UPDATE_BY_ID_SQL,
                toVoucherParamMap(voucherEntity));
        if (update != 1) {
            throw new RuntimeException("Nothing was Updated");
        }
        return voucherEntity;
    }

    @Override
    public List<VoucherEntity> findAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, voucherEntityRowMapper);
    }

    @Override
    public Optional<VoucherEntity> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID_SQL,
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    voucherEntityRowMapper));
        } catch (EmptyResultDataAccessException e) {
            logger.error("Got Empty result", e);
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(DELETE_ALL_SQL, Collections.emptyMap());
    }

    @Override
    public void deleteById(UUID voucherId) {
        var voucherIdMap = Collections.singletonMap("voucherId", voucherId.toString().getBytes());
        jdbcTemplate.update(DELETE_BY_ID_SQL, voucherIdMap);
    }
}
