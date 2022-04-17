package org.prgrms.kdt.repository;

import java.util.List;
import org.prgrms.kdt.domain.Voucher;

public interface VoucherRepository {

  Voucher save(Voucher voucher);

  List<Voucher> findAll();
}