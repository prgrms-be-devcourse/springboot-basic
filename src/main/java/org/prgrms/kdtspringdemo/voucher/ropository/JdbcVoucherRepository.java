package org.prgrms.kdtspringdemo.voucher.ropository;

import org.prgrms.kdtspringdemo.util.queryBuilder.constant.Column;
import org.prgrms.kdtspringdemo.util.queryBuilder.query.DeleteBuilder;
import org.prgrms.kdtspringdemo.util.queryBuilder.query.InsertBuilder;
import org.prgrms.kdtspringdemo.util.queryBuilder.query.SelectBuilder;
import org.prgrms.kdtspringdemo.util.queryBuilder.query.UpdateBuilder;
import org.prgrms.kdtspringdemo.util.queryBuilder.query.WhereBuilder;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static org.prgrms.kdtspringdemo.util.JdbcUtils.*;
import static org.prgrms.kdtspringdemo.util.queryBuilder.constant.Column.AMOUNT;
import static org.prgrms.kdtspringdemo.util.queryBuilder.constant.Column.VOUCHER_ID;
import static org.prgrms.kdtspringdemo.util.queryBuilder.constant.Column.VOUCHER_TYPE;
import static org.prgrms.kdtspringdemo.util.queryBuilder.constant.Table.VOUCHER;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID id = toUUID(resultSet.getBytes(VOUCHER_ID.getColumn()));
        VoucherType type = VoucherType.valueOf(resultSet.getString(VOUCHER_TYPE.getColumn()));
        long amount = resultSet.getLong(AMOUNT.getColumn());

        return type.createVoucher(id, amount);
    };

    private Map<String, Object> toParamMap(UUID voucherId, VoucherType voucherType, long amount) {
        return Map.of(
                VOUCHER_ID.getColumn(), uuidToBytes(voucherId),
                VOUCHER_TYPE.getColumn(), voucherType.name(),
                AMOUNT.getColumn(), amount
        );
    }

    @Override
    public Voucher save(Voucher voucher) {
        InsertBuilder saveQuery = new InsertBuilder()
                .insert(VOUCHER)
                .values(VOUCHER_ID, VOUCHER_TYPE, AMOUNT)
                .build();

        System.out.println(saveQuery.toString());
        jdbcTemplate.update(saveQuery.toString(), toParamMap(voucher.getId(), voucher.getType(), voucher.getAmount()));

        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        WhereBuilder where = new WhereBuilder()
                .equal(VOUCHER_ID)
                .build();

        SelectBuilder findByIdQuery = new SelectBuilder().select(Column.ALL)
                .from(VOUCHER)
                .where(where)
                .build();

        System.out.println(findByIdQuery.toString());
        return jdbcTemplate.query(findByIdQuery.toString(), Collections.singletonMap(VOUCHER_ID.getColumn(), uuidToBytes(id)), voucherRowMapper).stream()
                .findFirst();
    }

    @Override
    public List<Voucher> findAll() {
        SelectBuilder findAllQuery = new SelectBuilder()
                .select(Column.ALL)
                .from(VOUCHER)
                .build();

        System.out.println(findAllQuery.toString());
        return jdbcTemplate.query(findAllQuery.toString(), voucherRowMapper);
    }

    @Override
    public void upsert(UUID id, VoucherType type, long amount) {
        WhereBuilder where = new WhereBuilder()
                .equal(VOUCHER_ID)
                .build();

        UpdateBuilder updateQuery = new UpdateBuilder()
                .update(VOUCHER)
                .set(VOUCHER_TYPE, AMOUNT)
                .where(where)
                .build();

        System.out.println(updateQuery.toString());
        jdbcTemplate.update(updateQuery.toString(), toParamMap(id, type, amount));
    }

    @Override
    public void deleteById(UUID id) {
        WhereBuilder where = new WhereBuilder()
                .equal(VOUCHER_ID)
                .build();

        DeleteBuilder deleteByIdQuery = new DeleteBuilder()
                .delete(VOUCHER)
                .where(where)
                .build();

        jdbcTemplate.update(deleteByIdQuery.toString(), Collections.singletonMap(VOUCHER_ID.getColumn(), uuidToBytes(id)));
    }
}
