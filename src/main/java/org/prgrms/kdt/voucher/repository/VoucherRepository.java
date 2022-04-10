package org.prgrms.kdt.voucher.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.kdt.voucher.domain.Voucher;

public interface VoucherRepository {

  Voucher save(Voucher voucher);

  List<Voucher> findAll();
}