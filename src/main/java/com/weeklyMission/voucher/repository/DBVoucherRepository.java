package com.weeklyMission.voucher.repository;

import com.weeklyMission.voucher.domain.FixedAmountVoucher;
import com.weeklyMission.voucher.domain.Voucher;
import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
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
            throw new NoSuchElementException("존재하지 않는 id 입니다.", de);
        }
        return Optional.ofNullable(voucherId);
    }

    public List<Voucher> findByIds(List<String> ids){
        ids.add("dummy");
        List<Voucher> vouchers = jdbcTemplate.query(
            "select * from voucher where voucher_id in (:voucherIds)",
            Collections.singletonMap("voucherIds", ids), voucherRowMapper);

        return vouchers;
    }

    @Override
    public void deleteById(String id) {
        findById(id);
        jdbcTemplate.update("delete from voucher where voucher_id = :voucherId",
            Collections.singletonMap("voucherId", id));
    }

    static UUID toUUID(byte[] bytes){
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
