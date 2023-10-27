package team.marco.voucher_management_system.repository;

import java.util.List;
import java.util.UUID;

public interface WalletRepository {
    int link(String customerId, String voucherId);

    int unlink(String customerId, String voucherId);

    List<UUID> getVoucherIds(String customerId);

    List<UUID> getCustomerIds(String voucherId);
}
