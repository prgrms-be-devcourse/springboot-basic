package org.prgrms.orderApp.service;

import org.json.simple.parser.ParseException;
import org.prgrms.orderApp.domain.voucher.model.FixedAmountVoucher;
import org.prgrms.orderApp.domain.voucher.model.PercentDiscountVoucher;
import org.prgrms.orderApp.domain.voucher.model.Voucher;
import org.prgrms.orderApp.domain.voucher.model.VoucherType;
import org.prgrms.orderApp.domain.voucher.service.VoucherService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class VoucherApplicationService {

    private VoucherService voucherService;

    public VoucherApplicationService( VoucherService voucherService){
        this.voucherService = voucherService;
    }

    public boolean fixedAmountCheckInvalid(Long amountToCheck){
        return VoucherType.FIXED.invalidAmount(amountToCheck);
    }

    public boolean percentAmountCheckInvalid(Long amountToCheck){
        return VoucherType.PERCENT.invalidAmount(amountToCheck);
    }

    public PercentDiscountVoucher putPercentDiscountVoucher(Long amountToSave){
        return new PercentDiscountVoucher(UUID.randomUUID(), amountToSave);
    }

    public FixedAmountVoucher putFixedAmountVoucher(Long amountToSave){
        return new FixedAmountVoucher(UUID.randomUUID(), amountToSave);
    }

    public Map<String,Object> saveVoucher(Voucher voucher) throws IOException {
        return voucherService.saveVoucher(voucher);
    }

    public List<Voucher> getAllVouchers() throws IOException, ParseException {
        return voucherService.getAllVouchers();
    }
}
