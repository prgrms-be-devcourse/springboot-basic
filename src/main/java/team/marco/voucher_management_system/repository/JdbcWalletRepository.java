package team.marco.voucher_management_system.repository;

import java.io.ByteArrayInputStream;
import java.util.Collections;
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
    public int link(String customerId, String voucherId) {
        return jdbcTemplate.update(
                "INSERT INTO wallet(customer_id, voucher_id)"
                        + " VALUES (UUID_TO_BIN(:customerId), UUID_TO_BIN(:voucherId))",
                walletToMap(customerId, voucherId));
    }

    @Override
    public int unlink(String customerId, String voucherId) {
        return jdbcTemplate.update(
                "DELETE FROM wallet"
                        + " WHERE (customer_id, voucher_id) = (UUID_TO_BIN(:customerId), UUID_TO_BIN(:voucherId))",
                walletToMap(customerId, voucherId));
    }

    @Override
    public List<UUID> getVoucherIds(String customerId) {
        return jdbcTemplate.queryForList(
                        "SELECT voucher_id FROM wallet"
                                + " WHERE customer_id = UUID_TO_BIN(:customerId)",
                        Collections.singletonMap("customerId", customerId), ByteArrayInputStream.class)
                .stream()
                .map(byteArray -> UUIDConverter.convert(byteArray.readAllBytes()))
                .toList();
    }

    @Override
    public List<UUID> getCustomerIds(String voucherId) {
        return jdbcTemplate.queryForList(
                        "SELECT customer_id FROM wallet"
                                + " WHERE voucher_id = UUID_TO_BIN(:voucherId)",
                        Collections.singletonMap("voucherId", voucherId), ByteArrayInputStream.class)
                .stream()
                .map(byteArray -> UUIDConverter.convert(byteArray.readAllBytes()))
                .toList();
    }

    private Map<String, Object> walletToMap(String customerId, String voucherId) {
        return Map.ofEntries(
                Map.entry("customerId", customerId),
                Map.entry("voucherId", voucherId));
    }
}
