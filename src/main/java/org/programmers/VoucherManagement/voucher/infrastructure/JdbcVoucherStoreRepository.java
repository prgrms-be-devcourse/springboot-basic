package org.programmers.VoucherManagement.voucher.infrastructure;

import org.programmers.VoucherManagement.voucher.domain.Voucher;
import org.programmers.VoucherManagement.voucher.exception.VoucherException;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.UUID;

import static org.programmers.VoucherManagement.voucher.exception.VoucherExceptionMessage.*;

@Repository
@Primary
public class JdbcVoucherStoreRepository implements VoucherStoreRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherStoreRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        String sql = "insert into voucher_table(voucher_id, voucher_value , voucher_type) values (?,?,?)";
        int insertCount = jdbcTemplate.update(sql,
                voucher.getVoucherId().toString(),
                voucher.getDiscountValue().getValue(),
                voucher.getDiscountType().getType());

        if (insertCount != 1) {
            throw new VoucherException(FAIL_TO_INSERT);
        }
        return voucher;
    }

    @Override
    public void delete(UUID voucherId) {
        String sql = "delete from voucher_table where voucher_id = ?";
        int deleteCount = jdbcTemplate.update(sql,
                voucherId.toString());
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
}
