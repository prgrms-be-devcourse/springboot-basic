package org.programmers.springbootbasic.repository;

import org.programmers.springbootbasic.voucher.FixedDiscountVoucher;
import org.programmers.springbootbasic.voucher.RateDiscountVoucher;
import org.programmers.springbootbasic.voucher.Voucher;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.nio.ByteBuffer;
import java.util.*;

@Repository
public class JdbcTemplateVoucherRepository implements VoucherRepository {

    private final JdbcTemplate jdbcTemplate;

    public JdbcTemplateVoucherRepository(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public Voucher insert(Voucher voucher) {
        String sql = "insert into voucher(voucher_id, amount, type) values (?, ?, ?)";
        Map<String, Object> parameters = convertToParameterList(voucher);
        int updatedRow = jdbcTemplate.update(sql, parameters.get("voucher_id"), parameters.get("amount"), parameters.get("type"));
        if (updatedRow != 1) {
            throw new IllegalStateException("바우처가 정상적으로 저장되지 않았습니다.");
        }
        return voucher;
    }

    private Map<String, Object> convertToParameterList(Voucher voucher) {
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("voucher_id", uuidToByteArray(voucher.getId()));
        parameters.put("amount", voucher.getAmount());
        parameters.put("type", voucher.getType().toString());

        return parameters;
    }

    private byte[] uuidToByteArray(UUID id) {
        String uuidToHexString = id.toString().replace("-", "");
        return hexStringToByteArray(uuidToHexString);
    }

    public byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i+1), 16));
        }
        return data;
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {
        String sql = "select * from voucher where voucher_id = ?";
        return Optional.of(jdbcTemplate.queryForObject(sql, voucherRowMapper(), voucherId));
    }

    private RowMapper<Voucher> voucherRowMapper() {
        return (rs, rowNum) -> {
            Voucher voucher = null;
            String type = rs.getString("type");
            
            byte[] bytes = rs.getBytes("voucher_id");
            var byteBuffer = ByteBuffer.wrap(bytes);
            UUID voucherId = new UUID(byteBuffer.getLong(), byteBuffer.getLong());

            int amount = rs.getInt("amount");
            
            switch (type) {
                case "FIXED": return new FixedDiscountVoucher(voucherId, amount);
                case "RATE": return new RateDiscountVoucher(voucherId, amount);
                default: throw new IllegalStateException("잘못된 바우처 타입");
            }
        };
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "select * from voucher";
        return jdbcTemplate.query(sql, voucherRowMapper());
    }

    @Override
    public void remove(UUID voucherId) {
        String sql = "delete from voucher where voucher_id=?";
        jdbcTemplate.update(sql, uuidToByteArray(voucherId));
    }
}