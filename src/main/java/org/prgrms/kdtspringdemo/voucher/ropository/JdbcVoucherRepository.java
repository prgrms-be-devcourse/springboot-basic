package org.prgrms.kdtspringdemo.voucher.ropository;

import org.prgrms.kdtspringdemo.util.Query;
import org.prgrms.kdtspringdemo.voucher.constant.VoucherType;
import org.prgrms.kdtspringdemo.voucher.model.entity.Voucher;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static org.prgrms.kdtspringdemo.util.JdbcUtils.*;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository {
    private static final String VOUCHER_TABLE = "voucher";
    private static final String VOUCHER_ID = "voucher_id";
    private static final String VOUCHER_TYPE = "voucher_type";
    private static final String AMOUNT = "amount";

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID id = toUUID(resultSet.getBytes(VOUCHER_ID));
        VoucherType type = VoucherType.valueOf(resultSet.getString(VOUCHER_TYPE));
        long amount = resultSet.getLong(AMOUNT);

        return type.updateVoucher(id, amount);
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
        String saveQuery = new Query.QueryBuilder()
                .insertBuilder(VOUCHER_TABLE)
                .valuesBuilder(VOUCHER_ID, VOUCHER_TYPE, AMOUNT)
                .build();

        jdbcTemplate.update(saveQuery, toParamMap(voucher.getId(), voucher.getType(), voucher.getAmount()));

        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        String findByIdQuery = new Query.QueryBuilder()
                .selectBuilder("*")
                .fromBuilder(VOUCHER_TABLE)
                .whereCommonBuilder(VOUCHER_ID)
                .build();

        return jdbcTemplate.query(findByIdQuery, Collections.singletonMap(VOUCHER_ID, uuidToBytes(id)), voucherRowMapper)
                .stream()
                .findFirst();
    }

    @Override
    public List<Voucher> findAll() {
        String findAllQuery = new Query.QueryBuilder()
                .selectBuilder("*")
                .fromBuilder(VOUCHER_TABLE)
                .build();

        return jdbcTemplate.query(findAllQuery, voucherRowMapper);
    }

    @Override
    public void update(UUID id, VoucherType type, long amount) {
        String updateQuery = new Query.QueryBuilder()
                .updateBuilder(VOUCHER_TABLE)
                .setBuilder(VOUCHER_TYPE)
                .addSetBuilder(AMOUNT)
                .build();

        jdbcTemplate.update(updateQuery, toParamMap(id, type, amount));
    }

    @Override
    public void deleteById(UUID id) {
        String deleteByIdQuery = new Query.QueryBuilder()
                .deleteBuilder(VOUCHER_TABLE)
                .whereCommonBuilder(VOUCHER_ID)
                .build();

        jdbcTemplate.update(deleteByIdQuery, Collections.singletonMap(VOUCHER_ID, uuidToBytes(id)));
    }
}
