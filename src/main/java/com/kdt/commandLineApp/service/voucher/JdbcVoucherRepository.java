package com.kdt.commandLineApp.service.voucher;

import com.kdt.commandLineApp.exception.WrongVoucherParamsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository{
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        long voucherId = resultSet.getInt("id");
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
        Map<String,Object> paramMap = new HashMap<>();

        paramMap.put("type",voucher.getType());
        paramMap.put("amount", voucher.getDiscountAmount());
        namedParameterJdbcTemplate.update(
                "insert into mysql.voucher(type, amount) values(:type,:amount)",
                paramMap
        );
    }

    @Override
    public void remove(long id) {
        namedParameterJdbcTemplate.update(
                "delete from mysql.voucher where id = :voucherId",
                Collections.singletonMap("voucherId", id)
        );
    }

    @Override
    public Optional<Voucher> get(long id) {
        try {
            return Optional.ofNullable(namedParameterJdbcTemplate.queryForObject(
                    "select * from mysql.voucher where id = :voucherId",
                    Collections.singletonMap("voucherId", id),
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
        String sql;

        if ((page < 0) || (size < 0)) {
            return List.of();
        }
        if (type == null) {
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
            paramMap.put("type", type);
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
