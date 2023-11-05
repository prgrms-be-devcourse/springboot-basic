package team.marco.voucher_management_system.repository;

import java.util.List;
import java.util.UUID;

public interface WalletRepository {
    int link(UUID customerId, UUID voucherId);

    int unlink(UUID customerId, UUID voucherId);

    List<UUID> getVoucherIds(UUID customerId);

    List<UUID> getCustomerIds(UUID voucherId);
}
