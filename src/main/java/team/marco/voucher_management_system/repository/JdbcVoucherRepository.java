package team.marco.voucher_management_system.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import team.marco.voucher_management_system.model.LoadedVoucher;
import team.marco.voucher_management_system.model.Voucher;
import team.marco.voucher_management_system.type_enum.VoucherType;
import team.marco.voucher_management_system.util.UUIDConverter;

@Profile("prod")
@Repository
public class JdbcVoucherRepository implements VoucherRepository {
    private static final RowMapper<Voucher> voucherRowMapper = JdbcVoucherRepository::mapToVoucher;

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static Voucher mapToVoucher(ResultSet resultSet, int ignored) throws SQLException {
        byte[] idBytes = resultSet.getBytes("id");
        String typeString = resultSet.getString("type");
        String dataString = resultSet.getString("data");

        UUID id = UUIDConverter.convert(idBytes);
        VoucherType type = VoucherType.valueOf(typeString);
        int data = Integer.parseInt(dataString);
        LocalDateTime createAt = resultSet.getTimestamp("created_at").toLocalDateTime();

        LoadedVoucher loadedVoucher = new LoadedVoucher(id, type, data, createAt);

        return VoucherType.convertVoucher(loadedVoucher);
    }

    @Override
    public void save(Voucher voucher) {
        int updateCount = jdbcTemplate.update(
                "INSERT INTO voucher(id, type, data, created_at)"
                        + " VALUES (UUID_TO_BIN(:id), :type, :data, :created_at)",
                voucherToMap(voucher));

        if (updateCount == 0) {
            throw new NoSuchElementException("Insert fail voucher=%s".formatted(voucher));
        }
    }

    @Override
    public int deleteById(UUID id) {
        return jdbcTemplate.update(
                "DELETE FROM voucher WHERE id = UUID_TO_BIN(:id)",
                Collections.singletonMap("id", id.toString().getBytes()));
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("SELECT * FROM voucher", voucherRowMapper);
    }

    @Override
    public Optional<Voucher> findById(UUID id) {
        try {
            Voucher voucher = jdbcTemplate.queryForObject(
                    "SELECT * FROM voucher WHERE id = UUID_TO_BIN(:id)",
                    Collections.singletonMap("id", id.toString().getBytes()),
                    voucherRowMapper);

            return Optional.ofNullable(voucher);
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Voucher> findByType(VoucherType voucherType) {
        return jdbcTemplate.query(
                "SELECT * FROM voucher WHERE type = :type",
                Collections.singletonMap("type", voucherType.toString()),
                voucherRowMapper);
    }

    @Override
    public List<Voucher> findByCreateAt(LocalDateTime from, LocalDateTime to) {
        return jdbcTemplate.query(
                "SELECT * FROM voucher WHERE created_at BETWEEN :from AND :to",
                mapToTimestamp(from, to),
                voucherRowMapper);
    }

    private Map<String, Object> voucherToMap(Voucher voucher) {
        Timestamp createAt = Timestamp.valueOf(voucher.getCreateAt());

        return Map.ofEntries(
                Map.entry("id", voucher.getId().toString().getBytes()),
                Map.entry("type", voucher.getType().toString()),
                Map.entry("data", voucher.getData()),
                Map.entry("created_at", createAt));
    }

    private Map<String, Timestamp> mapToTimestamp(LocalDateTime from, LocalDateTime to) {
        Timestamp createdFrom = Timestamp.valueOf(from);
        Timestamp createdTo = Timestamp.valueOf(to);

        return Map.ofEntries(
                Map.entry("from", createdFrom),
                Map.entry("to", createdTo));
    }
}
