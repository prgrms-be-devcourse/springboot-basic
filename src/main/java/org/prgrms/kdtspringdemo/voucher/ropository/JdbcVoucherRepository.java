package org.prgrms.kdtspringdemo.voucher.ropository;

import org.prgrms.kdtspringdemo.util.Query;
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

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository {
    private static final String VOUCHER_TABLE = "voucher";
    private static final String VOUCHER_ID = "voucher_id";
    private static final String VOUCHER_TYPE = "voucher_type";
    private static final String AMOUNT = "amount";
    private static final String ALL = "*";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID id = toUUID(resultSet.getBytes(VOUCHER_ID));
        VoucherType type = VoucherType.valueOf(resultSet.getString(VOUCHER_TYPE));
        long amount = resultSet.getLong(AMOUNT);

        return type.createVoucher(id, amount);
    };

    private Map<String, Object> toParamMap(UUID voucherId, VoucherType voucherType, long amount) {
        return Map.of(
                VOUCHER_ID, uuidToBytes(voucherId),
                VOUCHER_TYPE, voucherType.name(),
                AMOUNT, amount
        );
    }

    @Override
    public Voucher save(Voucher voucher) {
        Query.InsertBuilder saveQuery = new Query.InsertBuilder()
                .insert(VOUCHER_TABLE)
                .values(VOUCHER_ID, VOUCHER_TYPE, AMOUNT)
                .build();

        jdbcTemplate.update(saveQuery.toString(), toParamMap(voucher.getId(), voucher.getType(), voucher.getAmount()));

        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        Query.SelectBuilder findByIdQuery = new Query.SelectBuilder()
                .select(ALL)
                .from(VOUCHER_TABLE)
                .where(VOUCHER_ID, VOUCHER_ID)
                .build();

        return jdbcTemplate.query(findByIdQuery.toString(), Collections.singletonMap(VOUCHER_ID, uuidToBytes(id)), voucherRowMapper).stream()
                .findFirst();
    }

    @Override
    public List<Voucher> findAll() {
        Query.SelectBuilder findAllQuery = new Query.SelectBuilder()
                .select(ALL)
                .from(VOUCHER_TABLE)
                .build();

        return jdbcTemplate.query(findAllQuery.toString(), voucherRowMapper);
    }

    @Override
    public void upsert(UUID id, VoucherType type, long amount) {
        Query.UpdateBuilder updateQuery = new Query.UpdateBuilder()
                .update(VOUCHER_TABLE)
                .set(VOUCHER_TYPE, VOUCHER_TYPE)
                .addSet(AMOUNT, AMOUNT)
                .build();

        jdbcTemplate.update(updateQuery.toString(), toParamMap(id, type, amount));
    }

    @Override
    public void deleteById(UUID id) {
        Query.DeleteBuilder deleteByIdQuery = new Query.DeleteBuilder()
                .delete(VOUCHER_TABLE)
                .where(VOUCHER_ID, VOUCHER_ID)
                .build();

        jdbcTemplate.update(deleteByIdQuery.toString(), Collections.singletonMap(VOUCHER_ID, uuidToBytes(id)));
    }
}
