package com.prgrms.springbootbasic.voucher.storage;

import com.prgrms.springbootbasic.common.exception.DataModifyingException;
import com.prgrms.springbootbasic.voucher.VoucherType;
import com.prgrms.springbootbasic.voucher.domain.Voucher;
import com.prgrms.springbootbasic.voucher.factory.VoucherFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.util.*;

@Profile("prod")
@Repository
public class JdbcVoucherRepository implements VoucherStorage {

    private enum VoucherQuery {
        INSERT("INSERT INTO voucher(voucher_id, voucher_type, discount_amount) VALUES(UNHEX(REPLACE(?, '-', '')), ?, ?)"),
        FIND_BY_ID("select * from voucher where voucher_id = UNHEX(REPLACE(?, '-', ''))"),
        FIND_ALL("select * from voucher"),
        UPDATE("update voucher set discount_amount = ? where voucher_id = UNHEX(REPLACE(?, '-', ''))"),
        DELETE("delete from voucher where voucher_id = UNHEX(REPLACE(?, '-', ''))");

        private final String query;

        VoucherQuery(String query) {
            this.query = query;
        }
    }

    public enum VoucherColumn {
        ID("voucher_id"),
        VOUCHER_TYPE("voucher_type"),
        DISCOUNT_AMOUNT("discount_amount");

        private final String column;

        VoucherColumn(String column) {
            this.column = column;
        }
    }

    private final JdbcTemplate jdbcTemplate;
    private final Map<VoucherType, VoucherFactory> voucherFactoryMap = new EnumMap<>(VoucherType.class);

    public JdbcVoucherRepository(DataSource dataSource, List<VoucherFactory> voucherFactories) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        voucherFactories.forEach(factory -> this.voucherFactoryMap.put(factory.getType(), factory));
    }

    private final RowMapper<Voucher> ROW_MAPPER = (resultSet, rowNum) -> {
        UUID voucherId = toUUID(resultSet.getBytes(VoucherColumn.ID.column));
        VoucherType voucherType = VoucherType.valueOf(resultSet.getString(VoucherColumn.VOUCHER_TYPE.column));
        int discountAmount = resultSet.getInt(VoucherColumn.DISCOUNT_AMOUNT.column);

        VoucherFactory voucherFactory = voucherFactoryMap.get(voucherType);
        return voucherFactory.mapToVoucher(voucherId, discountAmount);
    };

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer wrap = ByteBuffer.wrap(bytes);
        return new UUID(wrap.getLong(), wrap.getLong());
    }

    @Override
    public void save(Voucher voucher) {
        jdbcTemplate.update(VoucherQuery.INSERT.query,
                voucher.getUUID().toString(),
                voucher.getVoucherType().toString(),
                voucher.getDiscountRate());
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(VoucherQuery.FIND_ALL.query, ROW_MAPPER);
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                VoucherQuery.FIND_BY_ID.query,
                ROW_MAPPER,
                id.toString()));
    }

    @Override
    public void update(Voucher voucher) {
        int update = jdbcTemplate.update(VoucherQuery.UPDATE.query,
                voucher.getDiscountRate(),
                voucher.getUUID().toString());
        if (update == 0) {
            throw new DataModifyingException(
                    "Nothing was updated. query: " + VoucherQuery.UPDATE.query + " params: " + voucher.getUUID() + ", " + voucher.getDiscountRate());
        }
    }

    @Override
    public void delete(UUID id) {
        int update = jdbcTemplate.update(VoucherQuery.DELETE.query, id.toString());
        if (update == 0) {
            throw new DataModifyingException("Nothing was deleted. query: " + VoucherQuery.DELETE.query + " params: " + id);
        }
    }
}
