package org.prgrms.spring_week1.Voucher.repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.spring_week1.Voucher.model.FixedAmountVoucher;
import org.prgrms.spring_week1.Voucher.model.PercentDiscountVoucher;
import org.prgrms.spring_week1.Voucher.model.Voucher;
import org.prgrms.spring_week1.Voucher.model.VoucherStatus;
import org.prgrms.spring_week1.Voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import static org.prgrms.spring_week1.jdbcUtils.*;

@Repository
public class VoucherJdbcRepository implements VoucherRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private final static Logger logger = LoggerFactory.getLogger(VoucherJdbcRepository.class);

    public VoucherJdbcRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = toUUID(resultSet.getBytes("voucher_id"));
        VoucherType voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
        Long discount = resultSet.getLong("discount");
        VoucherStatus voucherStatus = VoucherStatus.valueOf(resultSet.getString("voucher_status"));
        LocalDateTime createdAt = toLocalDateTime(resultSet.getTimestamp("created_at"));
        LocalDateTime updatedAt = toLocalDateTime(resultSet.getTimestamp("updated_at"));

        if(voucherType == VoucherType.FIXEDAMOUNT){
            return new FixedAmountVoucher(voucherId, discount, voucherStatus, createdAt, updatedAt, voucherType);
        }else {
            return new PercentDiscountVoucher(voucherId, discount, voucherStatus, createdAt, updatedAt, voucherType);
        }
    };

    private Map<String, Object> toParamMap(Voucher voucher){
        HashMap<String, Object> objectHashMap = new HashMap<>();
        objectHashMap.put("voucherId", voucher.getVoucherId().toString().getBytes());
        objectHashMap.put("discount", voucher.getDiscount());
        objectHashMap.put("voucherType", voucher.getVoucherType().toString());
        objectHashMap.put("voucherStatus", voucher.getVoucherStatus().toString());
        objectHashMap.put("createdAt", voucher.getCreatedAt());
        objectHashMap.put("updatedAt", voucher.getUpdatedAt());


        return objectHashMap;

    }



    // CRUD

    @Override
    public Voucher insert(Voucher voucher) {
        int insert = jdbcTemplate.update(
            "insert into Vouchers(voucher_id, discount, status, type, created_at, updated_at)" +
                "values(UUID_TO_BIN(:voucherId), :discount, :voucherStatus, :voucherType, :createdAt, :updatedAt)",
            toParamMap(voucher));
        if(insert != 1){
            throw new RuntimeException("Nothing was inserted");
        }

        return voucher;
    }

    @Override
    public List<Voucher> getAllVoucher() {
        return jdbcTemplate.query("select * from Vouchers", voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
                return Optional.ofNullable(jdbcTemplate
                .queryForObject("select * from Vouchers where voucher_id = UUID_TO_BIN(:voucherId)",
                    Collections.singletonMap("voucherId", voucherId.toString().getBytes()), voucherRowMapper));
        } catch (EmptyResultDataAccessException e){
                logger.error("Got empty result :", e);
                return Optional.empty();
        }
    }

    @Override
    public Voucher update(Voucher voucher) {
             int update = jdbcTemplate.update("update Vouchers "
                     + "set discount = :discount, type = :voucherType, status = :voucherStatus, created_at = :createdAt, updated_at = :updatedAt "
                     + "where voucher_id = UUID_TO_BIN(voucherId)", toParamMap(voucher));
             if(update != 1){
                 throw new RuntimeException("Nothing was updated");
             }
        return voucher;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("delete from Vouchers", Collections.emptyMap()); }
}
