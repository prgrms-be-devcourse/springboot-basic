package org.prgrms.kdtspringdemo.voucher.ropository;

import org.prgrms.kdtspringdemo.util.queryBuilder.query.Delete;
import org.prgrms.kdtspringdemo.util.queryBuilder.query.Insert;
import org.prgrms.kdtspringdemo.util.queryBuilder.query.Select;
import org.prgrms.kdtspringdemo.util.queryBuilder.query.Update;
import org.prgrms.kdtspringdemo.util.queryBuilder.query.Where;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.Map.of;
import static org.prgrms.kdtspringdemo.util.JdbcUtils.*;
import static org.prgrms.kdtspringdemo.util.queryBuilder.constant.Operator.EQ;
import static org.prgrms.kdtspringdemo.voucher.constant.VoucherColumn.AMOUNT;
import static org.prgrms.kdtspringdemo.voucher.constant.VoucherColumn.VOUCHER_ID;
import static org.prgrms.kdtspringdemo.voucher.constant.VoucherColumn.VOUCHER_TYPE;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository {
    private final JdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID id = toUUID(resultSet.getBytes(VOUCHER_ID.getColumn()));
        VoucherType type = VoucherType.valueOf(resultSet.getString(VOUCHER_TYPE.getColumn()));
        long amount = resultSet.getLong(AMOUNT.getColumn());

        return type.createVoucher(id, amount);
    };

    @Override
    public Voucher save(Voucher voucher) {
        Insert insert = Insert.into(Voucher.class)
                .values(
                        of(
                                VOUCHER_ID.getColumn(), voucher.getId(),
                                VOUCHER_TYPE.getColumn(), voucher.getType().name(),
                                AMOUNT.getColumn(), voucher.getAmount()
                        )
                );
        jdbcTemplate.update(insert.getQuery());

        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        Select selectOne = Select.builder()
                .select()
                .from(Voucher.class)
                .where(
                        Where.builder(VOUCHER_ID.getColumn(), EQ, id)
                                .build()
                )
                .build();

        return jdbcTemplate.query(selectOne.getQuery(), voucherRowMapper).stream()
                .findFirst();
    }

    @Override
    public List<Voucher> findAll() {
        Select selectAll = Select.builder()
                .select()
                .from(Voucher.class)
                .build();

        return jdbcTemplate.query(selectAll.getQuery(), voucherRowMapper);
    }

    @Override
    public void upsert(UUID id, VoucherType type, long amount) {
        Update update = Update.builder()
                .update(Voucher.class)
                .set(of(
                        VOUCHER_TYPE.getColumn(), type.name(),
                        AMOUNT.getColumn(), amount
                ))
                .where(
                        Where
                                .builder(VOUCHER_ID.getColumn(), EQ, id)
                                .build()
                )
                .build();

        jdbcTemplate.update(update.getQuery());
    }

    @Override
    public void deleteById(UUID id) {
        Delete delete = Delete.builder()
                .delete(Voucher.class)
                .where(
                        Where
                                .builder(VOUCHER_ID.getColumn(), EQ, id)
                                .build()
                )
                .build();

        jdbcTemplate.update(delete.getQuery());
    }
}
