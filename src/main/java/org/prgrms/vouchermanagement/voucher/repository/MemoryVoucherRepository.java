package org.prgrms.vouchermanagement.voucher.repository;

/*@Repository
public class MemoryVoucherRepository implements VoucherRepository {
  private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

  @Override
  public Optional<Voucher> findById(UUID voucherId) {
    return Optional.ofNullable(storage.get(voucherId));
  }

  @Override
  public Voucher insert(Voucher voucher) {
    storage.put(voucher.getVoucherID(), voucher);
    return voucher;
  }

//  @Override
  public String findAll() {
    StringBuffer stringBuffer = new StringBuffer();

    for(UUID voucherId :storage.keySet()) {
      stringBuffer.append(storage.get(voucherId).toString() + "\n");
    }

    return stringBuffer.toString();
  }
}*/
