package com.prgrms.springbootbasic.repository.voucher;

import com.prgrms.springbootbasic.domain.voucher.Voucher;
import com.prgrms.springbootbasic.enums.VoucherType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.sql.DataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;


@Repository
@Slf4j
@Primary
public class VoucherJdbcRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate template;

    public VoucherJdbcRepository(DataSource dataSource) {
        this.template = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Voucher save(Voucher voucher) {
        String sql = "insert into vouchers values(:voucherId, :discountAmount, :voucherType, :createAt)";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("voucherId", voucher.getVoucherId())
                .addValue("discount", voucher.getDiscount())
                .addValue("voucherType", voucher.getVoucherType().toString())
                .addValue("createAt", voucher.getCreatedAt());

        template.update(sql, param);
        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String sql = "select * from vouchers where voucher_id = :voucherId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("voucherId", voucherId);

        Voucher voucher = template.queryForObject(sql, param, voucherRowMapper());
        return Optional.ofNullable(voucher);
    }

    @Override
    public Optional<Voucher> findByCreatedAt(LocalDateTime createAt) {
        String sql = "select * from vouchers where create_at = :createAt";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("createAt", createAt);

        Voucher voucher = template.queryForObject(sql, param, voucherRowMapper());
        return Optional.ofNullable(voucher);
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
    public Optional<Voucher> update(Voucher voucher) {
        String sql = "update vouchers set discount_amount = :discountAmount, voucher_type = :voucherType where voucher_id = :voucherId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("discountAmount", voucher.getDiscount());

        int rows = template.update(sql, param);
        if (rows == 1) {
            return Optional.of(voucher);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<Voucher> deleteById(UUID voucherId) {
        String sql = "delete from vouchers where voucher_id = :voucherId";

        SqlParameterSource param = new MapSqlParameterSource()
                .addValue("voucherId", voucherId);

        int deletedRows = template.update(sql, param);
        if (deletedRows == 1) {
            return Optional.of(null);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void deleteAll() {
        String sql = "delete from vouchers";

        SqlParameterSource param = new MapSqlParameterSource();

        template.update(sql, param);
    }

    private RowMapper<Voucher> voucherRowMapper() {
        RowMapper<Voucher> voucherRowMapper = (resultSet, rowMap) -> {
            UUID voucherId = UUID.fromString(resultSet.getString("voucher_id"));
            long discount = Long.parseLong(resultSet.getString("voucher_discount"));
            VoucherType voucherType = VoucherType.valueOf(resultSet.getString("Voucher_type").toUpperCase());
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
