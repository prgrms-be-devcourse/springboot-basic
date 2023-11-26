package com.weeklyMission.voucher.repository;

import com.weeklyMission.client.VoucherType;
import com.weeklyMission.voucher.domain.FixedAmountVoucher;
import com.weeklyMission.voucher.domain.Voucher;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.sql.DataSource;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;


@Repository
@Profile("prod")
public class DBVoucherRepository implements VoucherRepository{

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public DBVoucherRepository(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        String voucherId = resultSet.getString("voucher_id");
        String type = resultSet.getString("type");
        long amount = Long.parseLong(resultSet.getString("amount"));

        return VoucherFactory.of(type).makeVoucher(voucherId, amount);
    };

    private Map<String, Object> toParamMap(Voucher voucher){
        Map<String, Object> map = new HashMap<>();
        map.put("voucherId", voucher.getVoucherId());
        map.put("type", voucher instanceof FixedAmountVoucher?"fixed":"percent");
        map.put("amount", voucher.getAmount());
        return map;
    }

    @Override
    public Voucher save(Voucher voucher) {
        jdbcTemplate.update("INSERT INTO voucher(voucher_id, type, amount) VALUES (:voucherId, :type, :amount)", toParamMap(voucher));
        return voucher;
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("select * from voucher", voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(String id) {
        Voucher voucherId;
        try{
            voucherId = jdbcTemplate.queryForObject(
                "select * from voucher where voucher_id = :voucherId",
                Collections.singletonMap("voucherId", id), voucherRowMapper);
        } catch (EmptyResultDataAccessException de){
            return Optional.empty();
        }
        return Optional.ofNullable(voucherId);
    }

    @Override
    public List<Voucher> findByType(VoucherType type){
        return jdbcTemplate.query("select * from voucher where type = :type",
                Collections.singletonMap("type", type.getType()), voucherRowMapper);
    }

    @Override
    public List<Voucher> findByIds(List<String> ids){
        List<Voucher> vouchers;
        try{
            vouchers = jdbcTemplate.query(
                "select * from voucher where voucher_id in (:voucherIds)",
                Collections.singletonMap("voucherIds", ids), voucherRowMapper);
        } catch (Exception e){
            return new ArrayList<>();
        }

        return vouchers;
    }

    @Override
    public void deleteById(String id) {
        jdbcTemplate.update("delete from voucher where voucher_id = :voucherId",
            Collections.singletonMap("voucherId", id));
    }
}
