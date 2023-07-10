package org.programmers.VoucherManagement.voucher.infrastructure;

import org.programmers.VoucherManagement.voucher.domain.DiscountType;
import org.programmers.VoucherManagement.voucher.domain.Voucher;
import org.programmers.VoucherManagement.voucher.domain.VoucherFactory;
import org.programmers.VoucherManagement.voucher.exception.VoucherException;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.programmers.VoucherManagement.voucher.exception.VoucherExceptionMessage.*;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void save(Voucher voucher) {
        String sql = "insert into voucher_table(voucher_id, voucher_value , voucher_type) values (?,?,?)";
        int insertCount = jdbcTemplate.update(sql,
                voucher.getVoucherId().toString(),
                voucher.getDiscountValue().getValue(),
                voucher.getDiscountType().getType());

        if (insertCount != 1) {
            throw new VoucherException(FAIL_TO_INSERT);
        }
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "select * from voucher_table";
        return jdbcTemplate.query(sql, voucherRowMapper());
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String sql = "select * from voucher_table where voucher_id = ?";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql,
                    voucherRowMapper(),
                    voucherId.toString()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(Voucher voucher) {
        String sql = "delete from voucher_table where voucher_id = ?";
        int deleteCount = jdbcTemplate.update(sql,
                voucher.getVoucherId().toString());
        if (deleteCount != 1) {
            throw new VoucherException(FAIL_TO_DELETE);
        }
    }

    @Override
    public void update(Voucher voucher) {
        String sql = "update voucher_table set voucher_value = ? where voucher_id = ?";
        int updateCount = jdbcTemplate.update(sql,
                voucher.getDiscountValue().getValue(),
                voucher.getVoucherId().toString());
        if (updateCount != 1) {
            throw new VoucherException(FAIL_TO_UPDATE);
        }
    }


    public static RowMapper<Voucher> voucherRowMapper() {
        return (result, rowNum) -> VoucherFactory.mapVoucher(
                UUID.fromString(result.getString("voucher_id")),
                result.getInt("voucher_value"),
                DiscountType.from(result.getString("voucher_type"))
        );
    }
}
