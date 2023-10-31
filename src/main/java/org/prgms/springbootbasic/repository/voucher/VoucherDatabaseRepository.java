package org.prgms.springbootbasic.repository.voucher;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.domain.VoucherType;
import org.prgms.springbootbasic.domain.voucher.VoucherPolicy;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.*;

import static org.prgms.springbootbasic.common.UtilMethod.bytesToUUID;

@Repository
@Slf4j
@Profile({"dev", "prod"})
public class VoucherDatabaseRepository implements VoucherRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public VoucherDatabaseRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public VoucherPolicy upsert(VoucherPolicy voucher) {
        Optional<VoucherPolicy> foundVoucher = findById(voucher.getVoucherId());

        if (foundVoucher.isPresent()){
            jdbcTemplate.update("UPDATE vouchers SET discount_degree = :discountDegree, voucher_type = :voucherType WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))",
                    toParamMap(voucher));
        } else {
            jdbcTemplate.update("INSERT INTO vouchers(voucher_id, discount_degree, voucher_type) " +
                            "VALUES (UNHEX(REPLACE(:voucherId, '-', '')), :discountDegree, :voucherType)",
                    toParamMap(voucher));
        }

        return voucher;
    }

    @Override
    public Optional<VoucherPolicy> findById(UUID voucherId) {
        try {
            return Optional.ofNullable(
                    jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))",
                            Collections.singletonMap("voucherId", voucherId.toString().getBytes()),
                            mapToVoucher));
        } catch (EmptyResultDataAccessException e) {
            log.info("voucherId에 해당하는 Voucher가 DB에 없음.");
            return Optional.empty();
        }
    }

    @Override
    public List<VoucherPolicy> findAll() {
        return jdbcTemplate.query("SELECT * FROM vouchers", mapToVoucher);
    }

    @Override
    public void deleteById(UUID voucherId) {
        Optional<VoucherPolicy> deleteVoucher = findById(voucherId);

        jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = UNHEX(REPLACE(:voucherId, '-', ''))",
                Collections.singletonMap("voucherId", voucherId.toString().getBytes()));
        return deleteVoucher;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM vouchers", Collections.emptyMap());
    }

    private static Map<String, Object> toParamMap(VoucherPolicy voucher) {
        return new HashMap<>(){{
            put("voucherId", voucher.getVoucherId().toString().getBytes());
            put("discountDegree", voucher.getDiscountDegree());
            put("voucherType", voucher.getClass().getSimpleName());
        }};
    }

    private static RowMapper<VoucherPolicy> mapToVoucher = (rs, rowNum) -> {
        UUID voucherId = bytesToUUID(rs.getBytes("voucher_id"));
        long discountDegree = rs.getLong("discount_degree");
        String voucherTypeString = rs.getString("voucher_type");
        VoucherType voucherType = Arrays.stream(VoucherType.values())
                .filter(vt -> vt.getDisplayName().equals(voucherTypeString))
                .findAny()
                .orElseThrow(() -> new NoSuchElementException("해당 VoucherType이 존재하지 않음."));

        return voucherType.create(voucherId, discountDegree);
    };


}
