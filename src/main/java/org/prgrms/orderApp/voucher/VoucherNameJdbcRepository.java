package org.prgrms.orderApp.voucher;

import org.json.simple.parser.ParseException;
import org.prgrms.orderApp.customer.Customer;
import org.prgrms.orderApp.util.library.Common;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

@Repository
@Profile("local")
@Primary
public class VoucherNameJdbcRepository implements VoucherRepository {
    private static final Logger logger = LoggerFactory.getLogger(VoucherNameJdbcRepository.class);

    private final String SELECT_ALL_SQL = "select * from vouchers";
    private final String INSERT_SQL = "insert into vouchers(voucher_id, amount, voucherType) value (UUID_TO_BIN(:voucherId),:amount,:voucherType)";

    private final DataSource dataSource;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VoucherNameJdbcRepository(DataSource dataSource, NamedParameterJdbcTemplate jdbcTemplate){
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }
    private Map<String, Object> toParameter(Voucher voucher) {
        return new HashMap<>() {{
            put("voucherId", voucher.getVoucherId().toString().getBytes(StandardCharsets.UTF_8));
            put("amount", voucher.getVoucherAmount());
            put("voucherType", voucher.getClass().getName());

        }};
    }
    private Voucher mapToCustomerByJdbcTemplate(ResultSet resultSet) throws SQLException {
        return VoucherType.
                getVoucherTypeByVoucherClassName(resultSet.getString("voucherType"))
                .get()
                .getVoucher(
                        Common.toUUID(resultSet.getBytes("voucher_id")),
                        resultSet.getLong("amount")
                );

    }
    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        return null;
    }

    @Override
    public int insert(Voucher voucher) throws IOException {
        var status = 1;
        var insert = jdbcTemplate.update(INSERT_SQL, toParameter(voucher));
        if (insert != 1) status = 0;
        return status;
    }

    @Override
    public List<Voucher> findAll() throws IOException, ParseException {
        return jdbcTemplate.query(SELECT_ALL_SQL, (resultSet, i)-> mapToCustomerByJdbcTemplate(resultSet));
    }
}
