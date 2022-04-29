package com.prgrms.kdt.springbootbasic.repository;

import com.prgrms.kdt.springbootbasic.VoucherList;
import com.prgrms.kdt.springbootbasic.entity.voucher.Voucher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

@Repository
@Profile("!jdbc")
public class JdbcVoucherRepository implements VoucherRepository{
    private final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);
    private final DataSource dataSource;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(DataSource dataSource, NamedParameterJdbcTemplate jdbcTemplate) {
        this.dataSource = dataSource;
        this.jdbcTemplate = jdbcTemplate;
    }

    Map<String, Object> toParamMap(Voucher voucher){
        return new HashMap<>(){{
           put("voucher_id", voucher.getVoucherId().toString().getBytes());
           put("amount", voucher.getDiscountAmount());
           put("created_at", Timestamp.valueOf(voucher.getCreatedAt()));
           put("voucher_type", voucher.getVoucherType());
        }};
    }

    public static RowMapper<Voucher> voucherRowMapper = (resultSet, i) ->{
        var byteBuffer = ByteBuffer.wrap(resultSet.getBytes("voucher_id"));
        UUID voucherId = new UUID(byteBuffer.getLong(),byteBuffer.getLong());
        long amount = resultSet.getInt("amount");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        String voucherType = resultSet.getString("voucher_type");
        return VoucherList.makeVoucherByType(voucherType,voucherId,amount,createdAt);
    };

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            Voucher findVoucher = jdbcTemplate.queryForObject("SELECT * FROM vouchers where voucher_id = UNHEX(REPLACE( :voucher_id, '-', ''))",
                    Collections.singletonMap("voucher_id", voucherId.toString().getBytes()), voucherRowMapper);
            return Optional.of(findVoucher);
        }catch (EmptyResultDataAccessException e){
            return Optional.empty();
        }
    }


    @Override
    public Optional<Voucher> saveVoucher(Voucher voucher) {
        var insertResult = jdbcTemplate.update("INSERT INTO vouchers(voucher_id, amount,created_at,voucher_type) VALUES(UNHEX(REPLACE( :voucher_id, '-', '')), :amount, :created_at, :voucher_type)",toParamMap(voucher));
        if (insertResult != 1){
            return Optional.empty();
        }
        return Optional.of(voucher);
    }

    @Override
    public List<Voucher> getAllVouchers() {
        return jdbcTemplate.query("Select * from vouchers",voucherRowMapper);
    }

    @Override
    public Optional<Voucher> updateVoucherAmount(Voucher voucher){
        var updateResult = jdbcTemplate.update("UPDATE vouchers SET amount = :amount WHERE voucher_id = UNHEX(REPLACE( :voucher_id, '-', ''))", toParamMap(voucher));
        if (updateResult != 1){
            return Optional.empty();
        }
        return Optional.of(voucher);
    }

    @Override
    public boolean deleteVoucher(Voucher voucher){
        var deleteResult = jdbcTemplate.update("DELETE FROM vouchers where voucher_id = UNHEX(REPLACE( :voucher_id, '-', ''))", Collections.singletonMap("voucher_id", voucher.getVoucherId().toString().getBytes()));
        if(deleteResult!=1)
            return false;
        return true;
    }
}
