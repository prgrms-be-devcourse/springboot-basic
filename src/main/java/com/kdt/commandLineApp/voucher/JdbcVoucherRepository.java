package com.kdt.commandLineApp.voucher;

import com.kdt.commandLineApp.exception.WrongVoucherParamsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

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

    public void giveVoucherToCustomer(UUID customerId) {
        //존재하는 customerId인 지 확인하고
        //vid가 null 인 경우에만 update해야 됩니다.
        namedParameterJdbcTemplate.update("update mysql.customer set vid = null where cid = UUID_TO_BIN(:customerId)",
                Collections.singletonMap("customerId",customerId.toString().getBytes())
        );
    }

    public void deleteVoucherFromCustomer(UUID customerId) {
        namedParameterJdbcTemplate.update("update mysql.customer set vid = null where cid = UUID_TO_BIN(:customerId)",
                Collections.singletonMap("customerId",customerId.toString().getBytes())
        );
    }

    public List<Voucher> getVouchers(UUID customerId) {
        //foreign Key에 해당하는 voucher를 반환한다.
        String sql = "select v.vid vid, v.type type, v.amount amount" +
                "from (select vid from mysql.customer where cid = UUID_TO_BIN(:customerId)) c, mysql.voucher v"+
                "where c.vid = v.vid";

        namedParameterJdbcTemplate.queryForObject(
                sql,
                Collections.singletonMap("customerId", customerId.toString().getBytes()),
                voucherRowMapper
        );
        return null;
    }

    @Override
    public void destroy() throws Exception {

    }
}
