package org.prgrms.kdtspringvoucher.cmdapp.console;

import org.prgrms.kdtspringvoucher.cmdapp.ServiceType;
import org.prgrms.kdtspringvoucher.entity.voucher.Voucher;

public interface Input {
    ServiceType getServiceType();
    Voucher getVoucher();
    String inputCustomerId();
    String inputVoucherId();
}
