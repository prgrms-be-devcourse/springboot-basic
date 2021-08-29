package org.prgrms.kdt.repository.voucher;

import com.zaxxer.hikari.HikariDataSource;
import org.prgrms.kdt.domain.customer.Customer;
import org.prgrms.kdt.domain.customer.RegularCustomer;
import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.PercentDiscountVoucher;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.enums.VoucherType;
import org.prgrms.kdt.repository.customer.RegularCustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Profile({"prod", "default"})
@Primary
public class DataBaseVoucherRepository implements VoucherRepository {

    private static final Logger logger = LoggerFactory.getLogger(DataBaseVoucherRepository.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID customerId = toUUID(resultSet, "customer_id");
        UUID voucherId = toUUID(resultSet, "voucher_id");
        int discount = resultSet.getInt("discount");
        String voucherType = resultSet.getString("voucher_type");
        if(VoucherType.getVoucherType(voucherType) == VoucherType.FIXED) {
            return new FixedAmountVoucher(customerId, voucherId, discount, VoucherType.FIXED);
        } else {
            return new PercentDiscountVoucher(customerId, voucherId, discount, VoucherType.DISCOUNT);
        }
    };

    public DataBaseVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return new HashMap<String, Object>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("customerId", voucher.getCustomerId().toString().getBytes());
            put("voucherType", voucher.getVoucherType().toString());
            put("discount", voucher.getDiscount());
            put("createdAt", Timestamp.valueOf(LocalDateTime.now()));
        }};
    }

    @Override
    public Optional<Voucher> save(Voucher voucher) {
        int update = jdbcTemplate.update("INSERT INTO vouchers(voucher_id, customer_id, voucher_type, discount, created_at) VALUES (UUID_TO_BIN(:voucherId), UUID_TO_BIN(:customerId), :voucherType, :discount, :createdAt)", toParamMap(voucher));
        if(update != 1){
            throw new RuntimeException("Nothing was inserted");
        }
        return Optional.of(voucher);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return Optional.empty();
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("SELECT * FROM vouchers", voucherRowMapper);
    }

    static private UUID toUUID(ResultSet resultSet, String columnLabel) throws SQLException {
        ByteBuffer byteBuffer = ByteBuffer.wrap(resultSet.getBytes(columnLabel));
        UUID uuid = new UUID(byteBuffer.getLong(), byteBuffer.getLong());
        return uuid;
    }
}
