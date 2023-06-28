package org.promgrammers.springbootbasic.domain.voucher.repository;

import org.promgrammers.springbootbasic.domain.voucher.model.Voucher;
import org.promgrammers.springbootbasic.domain.voucher.model.VoucherType;
import org.promgrammers.springbootbasic.domain.voucher.service.VoucherFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("jdbc")
public class JdbcVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);
    private final NamedParameterJdbcTemplate template;

    private static final String INSERT = "INSERT INTO vouchers(voucher_id, amount, voucher_type) VALUES(:voucherId, :amount, :voucherType)";
    private static final String FIND_BY_ID = "SELECT * FROM vouchers WHERE voucher_id = :voucherId";
    private static final String FIND_ALL = "SELECT * FROM vouchers";
    private static final String UPDATE = "UPDATE vouchers SET amount = :amount, voucher_type = :voucherType WHERE voucher_id = :voucherId";
    private static final String DELETE_ALL = "DELETE FROM vouchers";

    public JdbcVoucherRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    private SqlParameterSource createParameterSource(Voucher voucher) {
        SqlParameterSource paramSource = new MapSqlParameterSource()
                .addValue("voucherId", voucher.getVoucherId().toString())
                .addValue("amount", voucher.getAmount())
                .addValue("voucherType", voucher.getVoucherType().toString());
        return paramSource;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        SqlParameterSource parameterSource = createParameterSource(voucher);
        int insertCount = template.update(INSERT, parameterSource);

        if (insertCount != 1) {
            throw new DataAccessException("바우처 저장에 실패 했습니다. => " + voucher.getVoucherId()) {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };
        }
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            Map<String, Object> param = Map.of("voucherId", voucherId);
            Voucher voucher = template.queryForObject(FIND_BY_ID, param, voucherRowMapper);
            return Optional.ofNullable(voucher);
        } catch (EmptyResultDataAccessException e) {
            logger.error("찾지 못했습니다. => " + voucherId);
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll() {
        return template.query(FIND_ALL, voucherRowMapper);
    }

    @Override
    public Voucher update(Voucher voucher) {
        template.update(UPDATE, createParameterSource(voucher));
        return voucher;
    }

    @Override
    public void deleteAll() {
        template.update(DELETE_ALL, Collections.emptyMap());
    }

    private final RowMapper<Voucher> voucherRowMapper = ((rs, rowNum) -> {
        String voucherId = rs.getString("voucher_id");
        long amount = rs.getLong("amount");
        VoucherType voucherType = VoucherType.of(rs.getString("voucher_type"));

        return VoucherFactory.convertToVoucher(UUID.fromString(voucherId), voucherType, amount);
    });
}