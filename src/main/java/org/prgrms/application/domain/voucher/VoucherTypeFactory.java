package org.prgrms.application.domain.voucher;

import org.prgrms.application.service.FixedVoucherService;
import org.prgrms.application.service.VoucherService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoucherTypeFactory {

    private final List<VoucherService> voucherServices;

    public VoucherTypeFactory(List<VoucherService> voucherServices) {
        this.voucherServices = voucherServices;
    }

    public VoucherService getVoucherService(VoucherType voucherType){
        final VoucherService voucherService;

        switch (voucherType){
            case FIXED:
                voucherService = voucherServices.get(0);
                break;
            case PERCENT:
                voucherService =  voucherServices.get(1);
                break;
            default:
                throw new IllegalArgumentException("알 수 없는 voucherType입니다.");
        }

        return voucherService;
    }
}
