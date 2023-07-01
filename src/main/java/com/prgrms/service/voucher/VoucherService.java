package com.prgrms.service.voucher;

import com.prgrms.model.dto.VoucherRequest;
import com.prgrms.model.voucher.*;
import com.prgrms.repository.voucher.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherCreator voucherCreator;

    public Voucher createVoucher(VoucherRequest voucherRequest) {
        Discount discount = voucherRequest.getDiscount();
        VoucherType voucherType = voucherRequest.getVoucherType();

        Voucher voucher = voucherCreator.createVoucher(discount, voucherType);

        return voucherRepository.insert(voucher);
    }

    public VoucherRegistry getAllVoucherList() {
        return voucherRepository.getAllVoucherList();
    }

}
