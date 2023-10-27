package team.marco.voucher_management_system.repository;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcWalletRepository implements WalletRepository {
    @Override
    public int link(String customerId, String voucherId) {
        return 0;
    }

    @Override
    public int unlink(String customerId, String voucherId) {
        return 0;
    }

    @Override
    public List<UUID> getVoucherIds(String customerId) {
        return Collections.emptyList();
    }

    @Override
    public List<UUID> getCustomerIds(String voucherId) {
        return Collections.emptyList();
    }
}
