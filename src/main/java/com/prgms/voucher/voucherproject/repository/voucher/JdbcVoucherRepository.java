package com.prgms.voucher.voucherproject.repository.voucher;

import builder.Delete;
import builder.Insert;
import builder.Select;
import builder.Where;
import com.prgms.voucher.voucherproject.domain.voucher.Voucher;
import com.prgms.voucher.voucherproject.domain.voucher.VoucherResponse;
import com.prgms.voucher.voucherproject.domain.voucher.VoucherType;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static builder.Operator.Type.*;
import static com.prgms.voucher.voucherproject.util.JdbcUtils.UUID_TO_BIN;


@Component
@Primary
public class JdbcVoucherRepository implements VoucherRepository {
    public static final int SUCCESS_QUERY = 1;
    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Voucher voucher) {
        Map<String, Object> values = new HashMap<>();
        values.put("voucher_id", voucher.getId());
        values.put("voucher_type", voucher.getVoucherType().toString());
        values.put("discount", voucher.getDiscount());

        Insert insert = Insert.into(Voucher.class)
                .values(values)
                .build();

        int save = jdbcTemplate.update(insert.getQuery());

        if (save != SUCCESS_QUERY) {
            throw new IllegalArgumentException("바우처 저장에 실패하였습니다.");
        }
    }

    @Override
    public List<Voucher> findAll() {
        Select selectQueryAll = Select.builder()
                .select(VoucherResponse.class)
                .from(Voucher.class)
                .build();

        List<Voucher> vouchers = jdbcTemplate.query(selectQueryAll.getQuery(), voucherRowMapper);
        return vouchers;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        Select select = Select.builder()
                .select(VoucherResponse.class)
                .from(Voucher.class)
                .where(
                        Where.builder("voucher_id", EQ, UUID_TO_BIN(voucherId))
                                .build()
                )
                .build();

        return jdbcTemplate.query(select.getQuery(), voucherRowMapper)
                .stream()
                .findFirst();
    }

    @Override
    public void deleteById(UUID voucherId) {
        Delete delete = Delete.from(Voucher.class)
                .where(
                        Where.builder("voucher_id", EQ, UUID_TO_BIN(voucherId))
                                .build()
                )
                .build();

        jdbcTemplate.update(delete.getQuery());
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        String voucherType = resultSet.getString("voucher_type");
        long discount = resultSet.getLong("discount");

        VoucherType createVoucherType = VoucherType.valueOf(voucherType);
        return createVoucherType.createVoucher(discount);
    };

}
