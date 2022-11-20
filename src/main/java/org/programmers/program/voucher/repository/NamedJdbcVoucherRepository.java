//package org.programmers.program.voucher.repository;
//
//import org.programmers.program.voucher.model.Voucher;
//import org.springframework.jdbc.core.RowMapper;
//import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
//import org.springframework.stereotype.Repository;
//
//import java.nio.ByteBuffer;
//import java.util.List;
//import java.util.Optional;
//import java.util.UUID;
//
//@Repository
//public class NamedJdbcVoucherRepository implements VoucherRepository{
//    private final NamedParameterJdbcTemplate namedJdbcTemplate;
//
//    private static final RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
//        var voucherId = toUUID(resultSet.getBytes("voucher_id"));
//        var customerEmail = resultSet.getString("email");
//        var customerId = toUUID(resultSet.getBytes("customer_id"));
//        var createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
//
//        return new Voucher(customerId, customerName, customerEmail, lastLoginAt, createdAt);
//    };
//
//    @Override
//    public Voucher insert(Voucher voucher) {
//        return null;
//    }
//
//    @Override
//    public Optional<Voucher> findById(UUID id) {
//        return Optional.empty();
//    }
//
//    @Override
//    public List<Voucher> findAll() {
//        return null;
//    }
//
//    static UUID toUUID(byte[]  bytes){
//        var byteBuffer = ByteBuffer.wrap(bytes);
//        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
//    }
//}
