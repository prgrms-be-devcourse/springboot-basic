package org.prgrms.kdt.storage;

import org.prgrms.kdt.voucher.Voucher;

import java.util.List;
import java.util.Optional;

public interface VoucherStorage {

    void save(Voucher voucher);

    List<Voucher> findAll();

    Optional<Voucher> findById(String voucherId);

    void deleteById(String voucherId);
}
