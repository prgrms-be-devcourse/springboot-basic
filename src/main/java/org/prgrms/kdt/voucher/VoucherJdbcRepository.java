package org.prgrms.kdt.voucher;

import java.sql.Timestamp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * Created by yhh1056
 * Date: 2021/08/30 Time: 6:48 오후
 */

@Repository
public class VoucherJdbcRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(VoucherRepository.class);

    private final JdbcTemplate jdbcTemplate;

    public VoucherJdbcRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher insert(Voucher voucher) {
        int update = jdbcTemplate.update(
                "INSERT INTO vouchers(voucher_id, voucher_type, discount, created_at) VALUES (UUID_TO_BIN(?), ?, ?, ?)",
                voucher.getVoucherId().toString().getBytes(),
                voucher.getVoucherType().name(),
                voucher.getDiscount(),
                Timestamp.valueOf(voucher.getCreatedAt()));

        if (update != 1) {
            throw new RuntimeException("Nothing was inserted");
        }

        return voucher;

    }

    public int count() {
        return jdbcTemplate.queryForObject("SELECT count(*) FROM vouchers", Integer.class);
    }
}
