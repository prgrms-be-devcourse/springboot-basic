package com.prgrms.springbootbasic.repository.voucher;

import com.prgrms.springbootbasic.domain.voucher.Voucher;
import com.prgrms.springbootbasic.enums.voucher.VoucherType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;


@Repository
@Slf4j
@Primary
//@Profile({"local", "test"})
public class VoucherJdbcRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate template;

    public VoucherJdbcRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Voucher save(Voucher voucher) {
        String sql = "insert into vouchers values(:voucherId, :discount, :voucherType, :voucherCreatedAt)";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("voucherId", voucher.getVoucherId().toString())
                .addValue("discount", voucher.getDiscount())
                .addValue("voucherType", voucher.getVoucherType().toString())
                .addValue("voucherCreatedAt", voucher.getCreatedAt());

        template.update(sql, param);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String sql = "select * from vouchers where voucher_id = :voucherId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("voucherId", voucherId.toString());

        Voucher voucher;
        try {
            voucher = template.queryForObject(sql, param, voucherRowMapper());
            return Optional.of(voucher);
        } catch (EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByCreatedAt() {
        String sql = "select * from vouchers ORDER BY voucher_createdAt ASC";

        List<Voucher> vouchers = template.query(sql, voucherRowMapper());
        return vouchers;
    }

    @Override
    public List<Voucher> findByType(VoucherType type) {
        String sql = "select * from vouchers where voucher_type = :voucherType";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("voucherType", type.toString());

        List<Voucher> vouchers = template.query(sql, param, voucherRowMapper());
        return vouchers;
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "select * from vouchers";

        List<Voucher> vouchers = template.query(sql, voucherRowMapper());
        return vouchers;
    }

    @Override
    public void update(Voucher voucher) {
        String sql = "update vouchers set discount = :discount where voucher_id = :voucherId";

        SqlParameterSource paramMap = new MapSqlParameterSource()
                .addValue("voucherId", voucher.getVoucherId().toString())
                .addValue("discount", voucher.getDiscount());

        template.update(sql, paramMap);
    }

    @Override
    public int deleteById(UUID voucherId) {
        String sql = "delete from vouchers where voucher_id = :voucherId";
        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("voucherId", voucherId.toString());
        return template.update(sql, param);
    }

    @Override
    public void deleteAll() {
        String sql = "delete from vouchers";

        SqlParameterSource param = new MapSqlParameterSource();

        template.update(sql, param);
    }

    @Override
    public boolean checkVoucherId(UUID voucherId) {
        String sql = "select * from vouchers where voucher_id = :voucherId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("voucherId", voucherId.toString());
        try {
            template.queryForObject(sql, param, voucherRowMapper());
            return true;
        } catch (EmptyResultDataAccessException e) {
            log.error("바우처 ID가 없어서 예외 발생", e.getMessage());
            return false;
        }
    }

    private RowMapper<Voucher> voucherRowMapper() {
        RowMapper<Voucher> voucherRowMapper = (resultSet, rowMap) -> {
            UUID voucherId = UUID.fromString(resultSet.getString("voucher_id"));
            long discount = Long.parseLong(resultSet.getString("discount"));
            VoucherType voucherType = VoucherType.valueOf(resultSet.getString("voucher_type").toUpperCase());
            LocalDateTime createdAt = resultSet.getTimestamp("voucher_createdAt").toLocalDateTime();

            return new Voucher() {
                @Override
                public UUID getVoucherId() {
                    return voucherId;
                }

                @Override
                public long getDiscount() {
                    return discount;
                }

                @Override
                public VoucherType getVoucherType() {
                    return voucherType;
                }

                @Override
                public LocalDateTime getCreatedAt() {
                    return createdAt;
                }
            };
        };
        return voucherRowMapper;
    }
}
