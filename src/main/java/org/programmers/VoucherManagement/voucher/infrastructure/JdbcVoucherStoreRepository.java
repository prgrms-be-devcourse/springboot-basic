package org.programmers.VoucherManagement.voucher.infrastructure;

import org.programmers.VoucherManagement.voucher.domain.Voucher;
import org.programmers.VoucherManagement.voucher.exception.VoucherException;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import static org.programmers.VoucherManagement.global.response.ErrorCode.*;

@Repository
@Primary
public class JdbcVoucherStoreRepository implements VoucherStoreRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherStoreRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        String sql = "INSERT INTO voucher_table(voucher_id, voucher_value, voucher_type, created_at) VALUES (?,?,?,?)";
        int insertCount = jdbcTemplate.update(sql,
                voucher.getVoucherId(),
                voucher.getDiscountValue().getValue(),
                voucher.getDiscountType().getType(),
                voucher.getCreatedAt()
        );

        if (insertCount != 1) {
            throw new VoucherException(FAIL_TO_INSERT_VOUCHER);
        }
        return voucher;
    }

    @Override
    public void delete(String voucherId) {
        String sql = "DELETE FROM voucher_table WHERE voucher_id = ?";
        int deleteCount = jdbcTemplate.update(sql,
                voucherId);
        if (deleteCount != 1) {
            throw new VoucherException(FAIL_TO_DELETE_VOUCHER);
        }
    }

    @Override
    public void update(Voucher voucher) {
        String sql = "UPDATE voucher_table SET voucher_value = ? WHERE voucher_id = ?";
        int updateCount = jdbcTemplate.update(sql,
                voucher.getDiscountValue().getValue(),
                voucher.getVoucherId());
        if (updateCount != 1) {
            throw new VoucherException(FAIL_TO_UPDATE_VOUCHER);
        }
    }
}
