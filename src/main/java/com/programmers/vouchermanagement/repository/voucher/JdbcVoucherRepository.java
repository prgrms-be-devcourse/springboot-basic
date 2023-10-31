package com.programmers.vouchermanagement.repository.voucher;


import com.programmers.vouchermanagement.domain.voucher.Voucher;
import com.programmers.vouchermanagement.domain.voucher.VoucherFactory;
import com.programmers.vouchermanagement.domain.voucher.VoucherType;
import com.programmers.vouchermanagement.dto.VoucherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
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
        SqlParameterSource namedParameters = new MapSqlParameterSource("id", id.toString());
        return jdbcTemplate.query(sql, namedParameters, (resultSet, i) -> mapToVoucher(resultSet)).stream().findFirst();
    }

    @Override
    public Optional<Voucher> findByName(String name) {
        String sql = "SELECT * FROM voucher WHERE name = :name";
        SqlParameterSource namedParameters = new MapSqlParameterSource("name", name);
        return jdbcTemplate.query(sql, namedParameters, (resultSet, i) -> mapToVoucher(resultSet)).stream().findFirst();
    }

    @Override
    public List<Voucher> findByNameLike(String name) {
        String sql = "SELECT * FROM voucher WHERE name LIKE :hasName";
        SqlParameterSource namedParameters = new MapSqlParameterSource("hasName", "%" + name + "%");
        return jdbcTemplate.query(
                sql,
                namedParameters,
                (resultSet, i) -> mapToVoucher(resultSet));
    }

    @Override
    @Transactional
    public Voucher save(Voucher voucher) {
        String sql = "INSERT INTO voucher (id, name, discount_amount, created_at, voucher_type) VALUES (UUID_TO_BIN(:id), :name, :discountAmount, :createdAt, :voucherType)";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", voucher.getId().toString())
                .addValue("name", voucher.getName())
                .addValue("discountAmount", voucher.getDiscountAmount())
                .addValue("createdAt", voucher.getCreatedAt())
                .addValue("voucherType", voucher.getVoucherType().toString());

        int affectedRow = jdbcTemplate.update(sql, namedParameters);
        logger.debug("Affected Row on save: {}", affectedRow);
        return voucher;
    }

    @Override
    @Transactional
    public int delete(UUID id) {
        String sql = "DELETE FROM voucher WHERE id = UUID_TO_BIN(:id)";
        SqlParameterSource namedParameters = new MapSqlParameterSource()
                .addValue("id", id.toString());
        int affectedRow = jdbcTemplate.update(sql, namedParameters);
        logger.debug("Affected Row on delete: {}", affectedRow);
        return affectedRow;
    }

    private Voucher mapToVoucher(ResultSet resultSet) throws SQLException {
        VoucherDto voucherDto = new VoucherDto(
                toUUID(resultSet.getBytes("id")),
                resultSet.getString("name"),
                resultSet.getFloat("discount_amount"),
                resultSet.getTimestamp("created_at").toLocalDateTime(),
                VoucherType.valueOf(resultSet.getString("voucher_type").toUpperCase())
        );
        return VoucherFactory.createVoucher(voucherDto);
    }

    private UUID toUUID(byte[] bytes) {
        final ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }
}
