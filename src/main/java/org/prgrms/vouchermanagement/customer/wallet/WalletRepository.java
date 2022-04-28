package org.prgrms.vouchermanagement.customer.wallet;

import java.util.List;
import java.util.UUID;

public interface WalletRepository {

  List<UUID> findVouchersByCustomerId(UUID customerId);

  List<UUID> findCustomersByVoucherId(UUID voucherId);

  void insert(UUID customerId, UUID voucherId);

  void delete(UUID customerId, UUID voucherId);

  void deleteAll();

  public int count();
}
