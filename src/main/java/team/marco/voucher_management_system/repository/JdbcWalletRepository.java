package team.marco.voucher_management_system.repository;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import team.marco.voucher_management_system.util.UUIDConverter;

@Repository
public class JdbcWalletRepository implements WalletRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public JdbcWalletRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public int link(UUID customerId, UUID voucherId) {
        return jdbcTemplate.update(
                "INSERT INTO wallet(customer_id, voucher_id)"
                        + " VALUES (UUID_TO_BIN(:customerId), UUID_TO_BIN(:voucherId))",
                walletToMap(customerId, voucherId));
    }

    @Override
    public int unlink(UUID customerId, UUID voucherId) {
        return jdbcTemplate.update(
                "DELETE FROM wallet"
                        + " WHERE (customer_id, voucher_id) = (UUID_TO_BIN(:customerId), UUID_TO_BIN(:voucherId))",
                walletToMap(customerId, voucherId));
    }

    @Override
    public List<UUID> getVoucherIds(UUID customerId) {
        List<ByteArrayInputStream> voucherIds = jdbcTemplate.queryForList(
                "SELECT voucher_id FROM wallet"
                        + " WHERE customer_id = UUID_TO_BIN(:customerId)",
                walletToMap(customerId, customerId),
                ByteArrayInputStream.class);

        return mapToUUID(voucherIds);
    }

    @Override
    public List<UUID> getCustomerIds(UUID voucherId) {
        return mapToUUID(jdbcTemplate.queryForList(
                "SELECT customer_id FROM wallet"
                        + " WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                walletToMap(voucherId, voucherId),
                ByteArrayInputStream.class));
    }

    private Map<String, Object> walletToMap(UUID customerId, UUID voucherId) {
        return Map.ofEntries(
                Map.entry("customerId", customerId.toString().getBytes()),
                Map.entry("voucherId", voucherId.toString().getBytes()));
    }

    private List<UUID> mapToUUID(List<ByteArrayInputStream> ids) {
        return ids.stream()
                .map(byteArray -> UUIDConverter.convert(byteArray.readAllBytes()))
                .toList();
    }
}
