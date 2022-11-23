package com.prgrms.springbootbasic.voucher.storage;

import com.prgrms.springbootbasic.common.exception.DataModifyingException;
import com.prgrms.springbootbasic.voucher.VoucherType;
import com.prgrms.springbootbasic.voucher.domain.Voucher;
import com.prgrms.springbootbasic.voucher.factory.VoucherFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.util.*;

@Profile("prod | test")
@Repository
public class JdbcVoucherRepository implements VoucherStorage {

    private static final String INSERT = "INSERT INTO voucher(voucher_id, voucher_type, discount_amount) VALUES(UNHEX(REPLACE(:voucher_id, '-', '')), :voucher_type, :discount_amount)";
    private static final String FIND_BY_ID = "select * from voucher where voucher_id = UNHEX(REPLACE(:voucher_id, '-', ''))";
    private static final String FIND_ALL = "select * from voucher";
    private static final String UPDATE = "update voucher set discount_amount = :discount_amount where voucher_id = UNHEX(REPLACE(:voucher_id, '-', ''))";
    private static final String DELETE = "delete from voucher where voucher_id = UNHEX(REPLACE(:voucher_id, '-', ''))";

    private static final String ID = "voucher_id";
    private static final String VOUCHER_TYPE = "voucher_type";
    private static final String DISCOUNT_AMOUNT = "discount_amount";

    private static final int ZERO = 0;

    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final Map<VoucherType, VoucherFactory> voucherFactoryMap = new EnumMap<>(VoucherType.class);

    public JdbcVoucherRepository(DataSource dataSource, List<VoucherFactory> voucherFactories) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        voucherFactories.forEach(factory -> this.voucherFactoryMap.put(factory.getType(), factory));
    }

    private final RowMapper<Voucher> ROW_MAPPER = (resultSet, rowNum) -> {
        UUID voucherId = toUUID(resultSet.getBytes(ID));
        VoucherType voucherType = VoucherType.valueOf(resultSet.getString(VOUCHER_TYPE));
        int discountAmount = resultSet.getInt(DISCOUNT_AMOUNT);

        VoucherFactory voucherFactory = voucherFactoryMap.get(voucherType);
        return voucherFactory.mapToVoucher(voucherId, discountAmount);
    };

    private static UUID toUUID(byte[] bytes) {
        ByteBuffer wrap = ByteBuffer.wrap(bytes);
        return new UUID(wrap.getLong(), wrap.getLong());
    }

    @Override
    public void save(Voucher voucher) {
        Map<String, Object> parameters = Map.of(
                ID, voucher.getUUID().toString(),
                VOUCHER_TYPE, voucher.getVoucherType().toString(),
                DISCOUNT_AMOUNT, voucher.getDiscountAmount());
        jdbcTemplate.update(INSERT, parameters);
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(FIND_ALL, ROW_MAPPER);
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(
                FIND_BY_ID,
                Collections.singletonMap(ID, id.toString()),
                ROW_MAPPER));
    }

    @Override
    public void update(Voucher voucher) {
        Map<String, Object> parameters = Map.of(
                DISCOUNT_AMOUNT, voucher.getDiscountAmount(),
                ID, voucher.getUUID().toString());
        int update = jdbcTemplate.update(UPDATE, parameters);
        if (update == ZERO) {
            throw new DataModifyingException(
                    "Nothing was updated. query: " + UPDATE + " params: " + voucher.getUUID() + ", " + voucher.getDiscountAmount());
        }
    }

    @Override
    public void delete(UUID id) {
        int update = jdbcTemplate.update(DELETE, Collections.singletonMap(ID, id.toString()));
        if (update == ZERO) {
            throw new DataModifyingException("Nothing was deleted. query: " + DELETE + " params: " + id);
        }
    }
}
