package org.prgrms.kdt.factory;

import org.prgrms.kdt.domain.InputType;
import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.domain.VoucherType;

import java.util.UUID;

public interface VoucherFactory {

    //Voucher을 받는 factory 정의
    Voucher getDiscounterVoucher(VoucherType voucherType);

}
