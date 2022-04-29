package org.prgrms.kdtspringvoucher.cmdapp.console;

import org.prgrms.kdtspringvoucher.cmdapp.ServiceType;
import org.prgrms.kdtspringvoucher.voucher.entity.Voucher;

public interface Input {
    ServiceType getServiceType();
    Voucher getVoucher();
}
