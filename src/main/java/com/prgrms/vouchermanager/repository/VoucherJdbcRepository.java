package com.prgrms.vouchermanager.repository;

import com.prgrms.vouchermanager.domain.Customer;
import com.prgrms.vouchermanager.domain.FixedAmountVoucher;
import com.prgrms.vouchermanager.domain.PercentAmountVoucher;
import com.prgrms.vouchermanager.domain.Voucher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.util.List;
import java.util.UUID;

@Slf4j
@Repository
public class VoucherJdbcRepository implements VoucherRepository {

    private final JdbcTemplate jdbcTemplate;

    private static final String INSERT_QUERY = "insert into vouchers(voucher_id, voucher_type, discount) values(UUID_TO_BIN(?), ?, ?)";
    private static final String LIST_QUERY = "select * from vouchers";
    private static final String UPDATE_DISCOUNT_QUERY = "update vouchers set discount=? where voucher_id=UUID_TO_BIN(?)";
    private static final String DELETE_QUERY = "delete from vouchers where voucher_id = UUID_TO_BIN(?)";

    public VoucherJdbcRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public Voucher create(Voucher voucher) {
        jdbcTemplate.update(INSERT_QUERY,
                voucher.getId().toString().getBytes(),
                voucher instanceof FixedAmountVoucher ? "fixed" : "percent",
                voucher.getDiscount());
        return voucher;
    }

    public List<Voucher> list() {
        return jdbcTemplate.query(LIST_QUERY, customerRowMapper());
    }

    public void updateDiscount(UUID id, int discount) {
        jdbcTemplate.update(UPDATE_DISCOUNT_QUERY, discount, id.toString().getBytes());
    }

    public UUID delete(UUID id) {
        jdbcTemplate.update(DELETE_QUERY, id.toString().getBytes());
        return id;
    }

    private RowMapper<Voucher> customerRowMapper() {
        return (rs, rowNum) -> {
            if(rs.getString("voucher_type").equals("fixed")) {
                return new FixedAmountVoucher(convertBytesToUUID(rs.getBytes("voucher_id")),
                        rs.getInt("discount"));
            } else {
                return new PercentAmountVoucher(convertBytesToUUID(rs.getBytes("voucher_id")),
                        rs.getInt("discount"));
            }
        };
    }

    private UUID convertBytesToUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        long high = byteBuffer.getLong();
        long low = byteBuffer.getLong();
        return new UUID(high, low);
    }
}
