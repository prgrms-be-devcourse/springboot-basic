package org.devcourse.voucher.application.voucher.repository;

import org.devcourse.voucher.core.exception.DataInsertFailException;
import org.devcourse.voucher.core.exception.DataUpdateFailException;
import org.devcourse.voucher.core.utils.JdbcUtils;
import org.devcourse.voucher.application.voucher.model.Voucher;
import org.devcourse.voucher.application.voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Repository
public class JdbcVoucherRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static Map<String, Object> toParamMap(Voucher voucher) {
        return Map.of(
                "voucherId", voucher.getVoucherId().toString().getBytes(),
                "name", voucher.getClass().getSimpleName(),
                "discount", voucher.getDiscount()
        );
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = JdbcUtils.toUUID(resultSet.getBytes("voucher_id"));
        long discount = resultSet.getLong("discount");
        return VoucherType.nameDiscriminate(resultSet.getString("name"))
                .voucherCreator(voucherId, discount);
    };

    @Override
    public Voucher insert(Voucher voucher) {
        logger.info("Repository : Record a voucher insert");
        int inserted = jdbcTemplate.update("INSERT INTO vouchers(voucher_id, name, discount) " +
                "VALUES(UUID_TO_BIN(:voucherId), :name, :discount)", toParamMap(voucher));
        if (inserted != 1) {
            throw new DataInsertFailException("Nothing was inserted");
        }
        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher) {
        logger.info("Repository : Record a voucher update");
        int updated = jdbcTemplate.update("UPDATE vouchers SET discount = :discount " +
                "WHERE voucher_id = UUID_TO_BIN(:voucherId)", toParamMap(voucher));
        if (updated != 1) {
            throw new DataUpdateFailException("Nothing was updated");
        }
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        logger.info("Repository : Record a voucher read");
        return jdbcTemplate.query("select * from vouchers", voucherRowMapper);
    }

    @Override
    public void deleteAll() {
        logger.info("Repository : Record a voucher delete");
        jdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
    }
}
