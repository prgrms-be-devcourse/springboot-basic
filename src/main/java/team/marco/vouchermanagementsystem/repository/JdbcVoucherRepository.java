package team.marco.vouchermanagementsystem.repository;

import java.nio.ByteBuffer;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.context.annotation.Profile;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import team.marco.vouchermanagementsystem.model.LoadedVoucher;
import team.marco.vouchermanagementsystem.model.Voucher;
import team.marco.vouchermanagementsystem.model.VoucherType;

@Profile("dev")
@Repository
public class JdbcVoucherRepository implements VoucherRepository {
    private static final RowMapper<Voucher> voucherRowMapper = JdbcVoucherRepository::mapToVoucher;

    private static Voucher mapToVoucher(ResultSet resultSet, int ignored) throws SQLException {
        byte[] idBytes = resultSet.getBytes("id");
        String typeString = resultSet.getString("type");
        String dataString = resultSet.getString("data");

        UUID id = bytesToUUID(idBytes);
        VoucherType type = VoucherType.valueOf(typeString);
        int data = Integer.parseInt(dataString);

        LoadedVoucher loadedVoucher = new LoadedVoucher(id, type, data);

        return VoucherType.convertVoucher(loadedVoucher);
    }

    private static UUID bytesToUUID(byte[] bytes) {
        ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);

        return new UUID(byteBuffer.getLong(), byteBuffer.getLong());
    }

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcVoucherRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void save(Voucher voucher) {
        int updateCount = jdbcTemplate.update(
                "INSERT INTO voucher(id, type, data) VALUES (UUID_TO_BIN(:id), :type, :data)",
                voucherToMap(voucher));

        if (updateCount != 1) {
            throw new DataAccessResourceFailureException("Insert query not committed");
        }
    }

    @Override
    public List<Voucher> findAll() {
        return jdbcTemplate.query("SELECT * FROM voucher", voucherRowMapper);
    }

    private Map<String, Object> voucherToMap(Voucher voucher) {
        return Map.ofEntries(
                Map.entry("id", voucher.getId().toString().getBytes()),
                Map.entry("type", voucher.getType().toString()),
                Map.entry("data", voucher.getData()));
    }
}
