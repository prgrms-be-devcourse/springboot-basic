package org.prgrms.kdt.factory;

import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.enumType.VoucherStatus;

public interface VoucherFactory {

    Voucher getDiscounterVoucher(int voucherStatus);


}
