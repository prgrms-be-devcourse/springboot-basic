package org.prgrms.springorder.domain.voucher.repository;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.model.VoucherType;
import org.prgrms.springorder.domain.voucher.service.VoucherFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
@Profile("dev")
public class VoucherJdbcRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(VoucherJdbcRepository.class);


    private static final String findByIdSql = "SELECT * FROM vouchers WHERE voucher_id = :voucherId";

    private static final String insertSql = "INSERT INTO vouchers(voucher_id, amount, voucher_type) VALUES (:voucherId, :amount, :voucherType)";

    private static final String selectAllSql = "SELECT * FROM vouchers";

    private static final String deleteAllSql = "DELETE  FROM vouchers";

    private static final String updateByIdSql = "UPDATE vouchers SET amount = :amount, voucher_type = :voucherType";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VoucherJdbcRepository(
        NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Voucher> voucherRowMapper = ((rs, rowNum) -> {

        String voucherId = rs.getString("voucher_id");
        long amount = rs.getLong("amount");
        VoucherType voucherType = VoucherType.of(rs.getString("voucher_type"));

        return VoucherFactory.toVoucher(voucherType, UUID.fromString(voucherId), amount);
    });

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString());
            put("amount", voucher.getAmount());
            put("voucherType", voucher.getVoucherType().getType());
        }};
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {

        try {
            Voucher findVoucher = jdbcTemplate.queryForObject(findByIdSql,
                Collections.singletonMap("voucherId", voucherId),
                voucherRowMapper);

            return Optional.ofNullable(findVoucher);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public Voucher insert(Voucher voucher) {

        try {
            jdbcTemplate.update(insertSql, toParamMap(voucher));

            return voucher;
        } catch (DataAccessException e) {
            logger.error("voucher insert error. name {},  message {}", e.getClass().getName(),
                e.getMessage());
            throw e;
        }

    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(selectAllSql, voucherRowMapper);
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update(deleteAllSql, Collections.emptyMap());
    }

    @Override
    public Voucher update(Voucher voucher) {
        jdbcTemplate.update(updateByIdSql, toParamMap(voucher));
        return voucher;
    }

}
