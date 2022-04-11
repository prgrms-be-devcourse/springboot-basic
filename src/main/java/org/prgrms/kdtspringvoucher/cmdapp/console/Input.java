package org.prgrms.kdtspringvoucher.cmdapp.console;

import org.prgrms.kdtspringvoucher.cmdapp.ServiceType;
import org.prgrms.kdtspringvoucher.voucher.service.Voucher;

public interface Input {
    ServiceType getServiceType();
    Voucher getVoucher();
}
