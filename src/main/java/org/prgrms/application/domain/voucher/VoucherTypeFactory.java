package org.prgrms.application.domain.voucher;

import org.prgrms.application.service.FixedVoucherService;
import org.prgrms.application.service.IntegratedVoucherService;
import org.prgrms.application.service.PercentVoucherService;
import org.prgrms.application.service.VoucherService;
import org.springframework.stereotype.Component;

@Component
public class VoucherTypeFactory {

    private FixedVoucherService fixedVoucherService;
    private PercentVoucherService percentVoucherService;
    private IntegratedVoucherService integratedVoucherService;

    public VoucherTypeFactory(FixedVoucherService fixedVoucherService, PercentVoucherService percentVoucherService) {
        this.fixedVoucherService = fixedVoucherService;
        this.percentVoucherService = percentVoucherService;
    }


    public VoucherService getType(VoucherType voucherType){
        final VoucherService voucherService;

        switch (voucherType){
            case FIXED:
                voucherService = fixedVoucherService;
                break;
            case PERCENT:
                voucherService = percentVoucherService;
                break;
            default:
                voucherService = integratedVoucherService;
        }
        return  voucherService;
    }
}
