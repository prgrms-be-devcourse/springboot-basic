package com.kdt.commandLineApp.service.voucher;

import com.kdt.commandLineApp.exception.WrongVoucherParamsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static com.kdt.commandLineApp.util.UUIDConverter.toUUID;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository{
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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

    @Autowired
    public JdbcVoucherRepository(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

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
    public void remove(String id) {
        namedParameterJdbcTemplate.update(
                "delete from mysql.voucher where vid = UUID_TO_BIN(:voucherId)",
                Collections.singletonMap("voucherId", id.getBytes())
        );
    }

    @Override
    public Optional<Voucher> get(String id) {
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(
                    "select * from mysql.voucher where vid = UUID_TO_BIN(:voucherId)",
                    Collections.singletonMap("voucherId", id.getBytes()),
                    voucherRowMapper
            ));
        }
        catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> getAll(int page, int size, String type) {
        Map<String, Object> paramMap = new HashMap<>();
        int voucherListSize;
        paramMap.put("type", type);
        String sql;

        if ((page < 0) || (size < 0)) {
            return List.of();
        }
        if (type == null) {
            Integer.toString(size);
            sql = "select * from mysql.voucher limit "+ size +" offset " + page * size;
            voucherListSize = this.size();
            if (page * size >= voucherListSize) {
                return List.of();
            }
            return namedParameterJdbcTemplate.query(
                    sql,
                    paramMap,
                    voucherRowMapper
            );
        }
        else {
            sql = "select * from mysql.voucher where type = :type limit "+ size +" offset " + page * size;
            voucherListSize = this.size(type);
            if (page * size >= voucherListSize) {
                return List.of();
            }
            return namedParameterJdbcTemplate.query(
                    sql,
                    paramMap,
                    voucherRowMapper
            );
        }
    }

    @Override
    public void deleteAll() {
        namedParameterJdbcTemplate.update("delete from mysql.voucher", Collections.emptyMap());
    }

    @Override
    public void destroy() throws Exception {

    }

    public int size() {
        return namedParameterJdbcTemplate.queryForObject(
                "select count(*) from mysql.voucher",
                Collections.emptyMap(),
                Integer.class
        );
    }

    public int size(String type) {
        return namedParameterJdbcTemplate.queryForObject(
                "select count(*) from mysql.voucher where type = :type",
                Collections.singletonMap("type", type),
                Integer.class
        );
    }
}
