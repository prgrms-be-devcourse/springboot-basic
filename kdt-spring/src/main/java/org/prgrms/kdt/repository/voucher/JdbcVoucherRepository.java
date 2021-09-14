package org.prgrms.kdt.repository.voucher;

import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.domain.voucher.VoucherSearch;
import org.prgrms.kdt.domain.voucher.VoucherType;
import org.prgrms.kdt.factory.VoucherFactory;
import org.springframework.context.annotation.Primary;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.nio.ByteBuffer;
import java.sql.Timestamp;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Transactional(readOnly = true)
@Repository
@Primary
public class JdbcVoucherRepository implements VoucherRepository {

    private final JdbcTemplate jdbcTemplate;

    public RowMapper<Voucher> voucherRowMapper = (resultSet, i) -> {
        UUID voucherId = mapToUUID(resultSet.getBytes("voucher_id"));
        VoucherType type = VoucherType.valueOf(resultSet.getString("type"));
        long value = resultSet.getLong("value");
        LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();

        return VoucherFactory.createVoucher(voucherId, type, value, createdAt);
    };

    public JdbcVoucherRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Transactional
    @Override
    public Voucher insert(Voucher voucher) throws RuntimeException{

        Assert.notNull(voucher, "Voucher should not be null");

        validateDuplicate(voucher);

        String insertSql = "INSERT INTO vouchers(voucher_id, value, type, created_at) VALUES(UUID_TO_BIN(?), ?, ?, ?)";

        int insert = jdbcTemplate.update(insertSql,
                mapToBytes(voucher.getVoucherId()),
                voucher.getValue(),
                voucher.getType().toString(),
                Timestamp.valueOf(voucher.getCreatedAt()));

        if (insert != 1)
            throw new RuntimeException("Nothing to inserted");

        return voucher;
    }

    @Override
    public Voucher update(Voucher voucher) {

        int update = jdbcTemplate.update("UPDATE vouchers SET value = ? WHERE voucher_id = UUID_TO_BIN(?)",
                voucher.getValue(),
                mapToBytes(voucher.getVoucherId()));

        if (update != 1)
            throw new RuntimeException("Nothing was updated");

        return voucher;
    }

    @Override
    public Optional<Voucher> find(Voucher voucher) {
        try {
            return Optional.of(jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE type = ? and value = ?",
                    voucherRowMapper,
                    voucher.getType().toString(),
                    voucher.getValue()));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll(VoucherSearch search) {
        StringBuilder searchQuery = new StringBuilder("SELECT * FROM vouchers WHERE 1=1");
        List queryArgs = new ArrayList<>();

        if (search.getVoucherType() != null) {
            searchQuery.append(" AND type = ?");
            queryArgs.add(search.getVoucherType().toString());
        }

        if (search.getCreatedAt() != null) {
            searchQuery.append(" AND DATE_FORMAT(created_at, '%Y-%m-%d') = DATE_FORMAT(?, '%Y-%m-%d')");
            queryArgs.add(Timestamp.valueOf(search.getCreatedAt()));
        }



        return jdbcTemplate.query(searchQuery.toString(), voucherRowMapper, mapToArray(queryArgs));
    }

    @Override
    public Optional<Voucher> findById(UUID voucherId) {

        Assert.notNull(voucherId, "Voucher id should not be null");

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject("SELECT * FROM vouchers WHERE voucher_id = UUID_TO_BIN(?)", voucherRowMapper, mapToBytes(voucherId)));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("SELECT * FROM vouchers", voucherRowMapper);
    }

    @Transactional
    @Override
    public void delete(Voucher voucher) {

        Assert.notNull(voucher, "Voucher should not be null");

        deleteById(voucher.getVoucherId());

    }

    @Transactional
    @Override
    public void deleteById(UUID voucherId) {

        Assert.notNull(voucherId, "Voucher id should not be null");

        int deleted = jdbcTemplate.update("DELETE FROM vouchers WHERE voucher_id = UUID_TO_BIN(?)", mapToBytes(voucherId));

        if(deleted != 1)
            throw new IllegalArgumentException("Nothing was deleted");
    }

    @Transactional
    @Override
    public void deleteAll() {
        for (Voucher voucher : findAll()) {
            deleteById(voucher.getVoucherId());
        }
    }


    private UUID mapToUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private byte[] mapToBytes(UUID voucherId) {
        return voucherId.toString().getBytes();
    }

    private void validateDuplicate(Voucher voucher) {
        Optional<Voucher> found = find(voucher);

        if (found.isPresent()) {
            throw new RuntimeException(MessageFormat.format("Voucher is already existed: type = {0}, value = {1}", voucher.getType().toString(), voucher.getValue()));
        }
    }

    private Object[] mapToArray(List queryArgs) {
        return queryArgs.toArray(new Object[queryArgs.size()]);
    }

}