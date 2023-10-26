package com.programmers.vouchermanagement.repository.voucher;


import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.domain.voucher.VoucherFactory;
import com.programmers.vouchermanagement.domain.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
@Profile("dev")
public class JdbcVoucherRepository implements VoucherRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final Logger logger = LoggerFactory.getLogger(JdbcVoucherRepository.class);

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Voucher> findAll() {
        String sql = "SELECT * FROM voucher";
        return jdbcTemplate.query(sql, (resultSet, i) -> mapToVoucher(resultSet));
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        String sql = "SELECT * FROM voucher WHERE id = UUID_TO_BIN(:id)";
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", id);
        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, namedParameters, (resultSet, i) -> mapToVoucher(resultSet)));
    }

    @Override
    public List<Voucher> findByName(String name) {
        String sql = "SELECT * FROM voucher WHERE name LIKE :hasName";
        SqlParameterSource namedParameters = new MapSqlParameterSource("hasName", "%" + name + "%");
        return jdbcTemplate.query(
                sql,
                namedParameters,
                (resultSet, i) -> mapToVoucher(resultSet));
    }

    @Override
    public Voucher save(Voucher voucher) {
        String sql = "INSERT INTO voucher (id, name, discount_amount, created_at, voucher_type) VALUES (:id, :name, :discountAmount, :createdAt, :voucherType)";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", voucher.getId())
                .addValue("name", voucher.getName())
                .addValue("discountAmount", voucher.getDiscountAmount())
                .addValue("createdAt", voucher.getCreatedAt())
                .addValue("voucherType", voucher.getVoucherType());

        int affectedRow = jdbcTemplate.update(sql, namedParameters);
        logger.debug("Affected Row on save: {}", affectedRow);
        return voucher;
    }

    @Override
    public void delete(UUID id) {
        String sql = "DELETE FROM voucher WHERE id = :id";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id);
        int affectedRow = jdbcTemplate.update(sql, namedParameters);
        logger.debug("Affected Row on delete: {}", affectedRow);
    }

    private Voucher mapToVoucher(ResultSet resultSet) throws SQLException {
        final UUID id = toUUID(resultSet.getBytes("id"));
        final String name = resultSet.getString("name");
        final float discountAmount = resultSet.getFloat("discount_amount");
        final LocalDateTime createdAt = resultSet.getTimestamp("created_at").toLocalDateTime();
        final VoucherType voucherType = VoucherType.valueOf(resultSet.getString("voucher_type"));
        return VoucherFactory.createVoucher(id, name, discountAmount, createdAt, voucherType);
    }

    private UUID toUUID(byte[] bytes) {
        final ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
