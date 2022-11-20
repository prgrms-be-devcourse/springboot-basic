package org.prgrms.vouchermanagement.voucher.repository;

import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.prgrms.vouchermanagement.voucher.domain.VoucherType;
import org.prgrms.vouchermanagement.voucher.domain.dto.VoucherCreateDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.util.*;

@Profile("default")
@Repository
public class JdbcVoucherRepository implements VoucherRepository {

    private final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final String SAVE_SQL = "INSERT INTO vouchers VALUES (UNHEX(REPLACE(:voucherId,'-','')), :discountAmount, :voucherType, UNHEX(REPLACE(:customerId,'-','')))";
    private static final String FIND_ALL_SQL = "SELECT * FROM vouchers";
    private static final String FIND_BY_ID_SQL = "SELECT * FROM vouchers WHERE voucher_id = UNHEX(REPLACE(:voucherId,'-',''))";
    private static final String DELETE_ALL_SQL = "DELETE FROM vouchers";
    private static final String DELETE_VOUCHERS_BY_CUSTOMER_ID_SQL = "DELETE FROM vouchers WHERE customer_id = UNHEX(REPLACE(:customerId,'-',''))";
    private static final String FIND_VOUCHERS_BY_CUSTOMER_ID_SQL = "SELECT * FROM vouchers WHERE customer_id = UNHEX(REPLACE(:customerId,'-',''))";

    private final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        return VoucherType.createVoucher(
                VoucherCreateDTO.of(
                        toUUID(resultSet.getBytes("voucher_id")),
                        resultSet.getString("voucher_type"),
                        resultSet.getInt("discount_amount"),
                        toUUID(resultSet.getBytes("customer_id"))));
    };

    @Autowired
    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Voucher save(Voucher voucher) {
        jdbcTemplate.update(SAVE_SQL, toParamMap(voucher));

        return voucher;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        try {
            return Optional.of(jdbcTemplate.queryForObject(FIND_BY_ID_SQL, Map.of("voucherId", voucherId.toString().getBytes()), voucherRowMapper));
        } catch (DataAccessException e) {
            logger.error("예외 발생 : {}", e.getMessage());
            System.out.println("존재하지 않는 바우처입니다.");
        }
        return Optional.empty();
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query(FIND_ALL_SQL, voucherRowMapper);
    }

    @Override
    public void clear() {
        jdbcTemplate.update(DELETE_ALL_SQL, Collections.emptyMap());
    }

    @Override
    public List<Voucher> findVouchersByCustomerId(UUID customerId) {
        return jdbcTemplate.query(FIND_VOUCHERS_BY_CUSTOMER_ID_SQL, Map.of("customerId", customerId.toString().getBytes()), voucherRowMapper);
    }

    @Override
    public void deleteVoucherByCustomerId(UUID customerId) {
        jdbcTemplate.update(DELETE_VOUCHERS_BY_CUSTOMER_ID_SQL, Map.of("customerId", customerId.toString().getBytes()));
    }

    private Map<String, Object> toParamMap(Voucher voucher) {
        return Map.of(
                "voucherId", voucher.getVoucherId().toString().getBytes(),
                "discountAmount", voucher.getDiscountAmount(),
                "voucherType", voucher.getVoucherType().name(),
                "customerId", voucher.getCustomerId().toString().getBytes()
        );
    }

    private UUID toUUID(byte[] bytes) {
        var byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
