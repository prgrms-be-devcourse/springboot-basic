package org.prgrms.application.domain.voucher;

import org.prgrms.application.service.VoucherService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoucherTypeFactory {

    private final List<VoucherService> voucherServices;

    public VoucherTypeFactory(List<VoucherService> voucherServices) {
        this.voucherServices = voucherServices;
    }

    public VoucherService getType(VoucherType voucherType){
        final VoucherService voucherService;

        switch (voucherType){
            case FIXED:
                voucherService = null;
                break;
            case PERCENT:
                voucherService = null;
                break;
            default:
                voucherService = null;
        }

        return  voucherService;
    }
}
