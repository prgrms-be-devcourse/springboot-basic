package com.kdt.commandLineApp.voucher;

import com.kdt.commandLineApp.exception.WrongVoucherParamsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static com.kdt.commandLineApp.UUIDConverter.toUUID;

@Repository
@Profile("db")
public class JdbcVoucherRepository implements VoucherRepository{
    @Autowired
    NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("vid"));
        String type = resultSet.getString("type");
        int amount = resultSet.getInt("amount");

        try {
            return new Voucher(voucherId, type, amount);
        }
        catch (WrongVoucherParamsException e) {
            e.printStackTrace();
        }
        return null;
    };

    @Override
    public void add(Voucher voucher) {
        Map<String,Object> paramMap = new HashMap<>() {{
            put("voucherId",voucher.getId().toString().getBytes());
            put("type",voucher.getType());
            put("amount",voucher.getDiscountAmount());
        }};

        namedParameterJdbcTemplate.update(
                "insert into mysql.voucher(vid, type, amount) values(UUID_TO_BIN(:voucherId),:type,:amount)",
                paramMap
        );
    }

    @Override
    public void remove(UUID voucherId) {
        namedParameterJdbcTemplate.update(
                "delete from mysql.voucher where vid = UUID_TO_BIN(:voucherId)",
                Collections.singletonMap("voucherId", voucherId.toString().getBytes())
        );
    }

    @Override
    public Optional<Voucher> get(UUID voucherId) {
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(
                    "select * from mysql.voucher where vid = UUID_TO_BIN(:voucherId)",
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                    voucherRowMapper
            ));
        }
        catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> getAll() {
        return namedParameterJdbcTemplate.query(
                "select * from mysql.voucher",
                voucherRowMapper
        );
    }

    public void deleteAll() {
        namedParameterJdbcTemplate.update("delete from mysql.voucher", Collections.emptyMap());
    }

    public boolean checkVoucherValidation(UUID voucherId) {
        String sql = "select count(*) from mysql.voucher where vid = UUID_TO_BIN(:voucherId)";
        int result = 0;

        result = namedParameterJdbcTemplate.update(
                sql,
                Collections.singletonMap("voucherId", voucherId.toString().getBytes())
        );
        return (result != 0);
    }

    public void giveVoucherToCustomer(UUID customerId, UUID voucherId) {
        String sql = "update mysql.customer set vid = UUID_TO_BIN(:voucherId) where cid = UUID_TO_BIN(:customerId)";
        Map<String, Object> paramMap = new ConcurrentHashMap<>() {{
            put("customerId",customerId.toString().getBytes());
            put("voucherId",voucherId.toString().getBytes());
        }};

        namedParameterJdbcTemplate.update(
                sql,
                paramMap
        );
    }

    public void deleteVoucherFromCustomer(UUID customerId) {
        String sql = "update mysql.customer set vid = null where cid = UUID_TO_BIN(:customerId)";

        namedParameterJdbcTemplate.update(
                sql,
                Collections.singletonMap("customerId",customerId.toString().getBytes())
        );
    }

    public List<Voucher> getCustomerVouchers(UUID customerId) {
        String sql = "select vid from mysql.customer where cid = UUID_TO_BIN(:customerId)";

        RowMapper<Optional<UUID>> uuidRowMapper = (resultSet, i) -> {
            return Optional.ofNullable(toUUID(resultSet.getBytes("vid")));
        };

        UUID cuuid = namedParameterJdbcTemplate.queryForObject(
                sql,
                Collections.singletonMap("customerId", customerId.toString().getBytes()),
                uuidRowMapper
        ).get();

        if (cuuid != null) {
            sql = "select * from mysql.voucher where vid = UUID_TO_BIN(:voucherId)";
            return namedParameterJdbcTemplate.query(
                    sql,
                    Collections.singletonMap("voucherId", cuuid.toString().getBytes()),
                    voucherRowMapper
            );
        }
        else {
            return new ArrayList<>();
        }
    }

    @Override
    public void destroy() throws Exception {

    }
}
