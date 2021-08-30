package org.prgrms.orderApp.application.service;

import org.json.simple.parser.ParseException;
import org.prgrms.orderApp.application.util.BasicCheckInvalid;
import org.prgrms.orderApp.domain.voucher.model.FixedAmountVoucher;
import org.prgrms.orderApp.domain.voucher.model.PercentDiscountVoucher;
import org.prgrms.orderApp.domain.voucher.model.Voucher;
import org.prgrms.orderApp.domain.voucher.model.VoucherType;
import org.prgrms.orderApp.domain.voucher.service.VoucherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class VoucherApplicationService {

    private BasicCheckInvalid checkInvalid;
    private VoucherService voucherService;

    public VoucherApplicationService(BasicCheckInvalid basicCheckInvalid, VoucherService voucherService){
        this.checkInvalid = basicCheckInvalid;
        this.voucherService = voucherService;
    }

    public boolean fixedAmountCheckInvalid(Long amountToCheck){
        return checkInvalid.checkInvalidAmount(0L, amountToCheck, VoucherType.FIXEDAMOUNT.getLimit());
    }

    public boolean percentAmountCheckInvalid(Long amountToCheck){
        return checkInvalid.checkInvalidAmount(0L, amountToCheck, VoucherType.PERCENTAMOUNT.getLimit());
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
