package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.model.VoucherType;

import java.util.List;
import java.util.UUID;

public interface IVoucherJdbcService extends VoucherService {

    void create(String voucherType, Long amount);

    void deleteById(UUID voucherId);

    List<Voucher> findByVoucherType(VoucherType voucherType);

    List<Voucher> findByVouchersTerm(String start, String end);
}
