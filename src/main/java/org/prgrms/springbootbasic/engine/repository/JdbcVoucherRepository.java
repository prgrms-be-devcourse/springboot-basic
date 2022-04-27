package org.prgrms.springbootbasic.engine.repository;

import org.prgrms.springbootbasic.engine.domain.Customer;
import org.prgrms.springbootbasic.engine.domain.Voucher;
import org.prgrms.springbootbasic.engine.enumtype.VoucherType;
import org.prgrms.springbootbasic.exception.VoucherException;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

import static org.prgrms.springbootbasic.engine.util.JdbcUtil.toUUID;

@Repository
@Profile({"test", "default"})
public class JdbcVoucherRepository implements VoucherRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, rowNum) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        VoucherType voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        Integer value = resultSet.getInt(voucherType.getValueColumnName());
        UUID customerId = resultSet.getBytes("customer_id") != null ? toUUID(resultSet.getBytes("customer_id")) : null;
        return voucherType.createVoucher(voucherId, customerId, value, createdAt);
    };

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<String, Object>() {{
           put("voucherId", voucher.getVoucherId().toString().getBytes());
           put("voucherType", voucher.getVoucherType().toString());
           put("createdAt", Timestamp.valueOf(voucher.getCreatedAt()));
           put("value", voucher.getValue());
           put("customerId", voucher.getCustomerId().isPresent() ? voucher.getCustomerId().get().toString().getBytes() : null);
        }};
    }

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from vouchers", voucherRowMapper);
    }

    @Override
    public int count() {
        return jdbcTemplate.query("select * from vouchers", voucherRowMapper).size();
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("select * from vouchers where voucher_id=UNHEX(REPLACE(:voucherId, '-', ''));",
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    voucherRowMapper));
        } catch(EmptyResultDataAccessException ex) {
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByType(VoucherType voucherType) {
        return jdbcTemplate.query("select * from vouchers where voucher_type=:voucherType;",
                Collections.singletonMap("voucherType", voucherType.toString()),
                voucherRowMapper);
    }

    @Override
    public List<Voucher> findByCustomer(Customer customer) {
        return jdbcTemplate.query("select * from vouchers where customer_id = UNHEX(REPLACE(:customerId, '-', ''));", Collections.singletonMap("customerId", customer.getCustomerId().toString().getBytes()),voucherRowMapper);
    }

    @Override
    public Voucher insert(Voucher voucher) {
        Map<String, Object> paramMap = toParamMap(voucher);
        int insertCount = jdbcTemplate.update("insert into vouchers (voucher_id, voucher_type, " + voucher.getVoucherType().getValueColumnName() + ", created_at) values (UNHEX(REPLACE(:voucherId, '-', '')), :voucherType, :value, :createdAt);",paramMap);
        if (insertCount != 1) {
            throw new VoucherException("Voucher cant be inserted!");
        }
        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher) {
        Map<String, Object> paramMap = toParamMap(voucher);
        int updateCount = jdbcTemplate.update("update vouchers set "+ voucher.getVoucherType().getValueColumnName() + "= :value, customer_id = UNHEX(REPLACE(:customerId, '-', '')) where voucher_id = UNHEX(REPLACE(:voucherId, '-', ''));",paramMap);
        if (updateCount < 1) {
            throw new VoucherException("Nothing was updated!");
        }
        return voucher;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from vouchers", Collections.emptyMap());
    }

    @Override
    public void deleteById(UUID voucherId) {
        int deleteCount = jdbcTemplate.update("delete from vouchers where voucher_id=UNHEX(REPLACE(:voucherId, '-', ''))", Collections.singletonMap("voucherId", voucherId.toString().getBytes()));
        if (deleteCount != 1) {
            throw new VoucherException("Voucher cant be deleted!");
        }
    }
}
