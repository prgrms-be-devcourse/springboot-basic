package com.prgrms.springbasic.service;

import com.prgrms.springbasic.domain.DiscountType;
import com.prgrms.springbasic.domain.FixedAmountVoucher;
import com.prgrms.springbasic.domain.PercentDiscountVoucher;
import com.prgrms.springbasic.domain.Voucher;
import com.prgrms.springbasic.dto.CreateVoucherRequest;
import com.prgrms.springbasic.dto.VoucherResponse;
import com.prgrms.springbasic.repository.VoucherRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherResponse saveVoucher(CreateVoucherRequest request) {
        Voucher voucher = voucherRepository.saveVoucher(createVoucher(request));
        return VoucherResponse.from(voucher);
    }

    public List<VoucherResponse> findAll() {
        return voucherRepository.findAll().stream()
                .map(VoucherResponse::from)
                .toList();
    }

    private Voucher createVoucher(CreateVoucherRequest request) {
        return switch (DiscountType.find(request.getDiscountType())) {
            case FIXED -> new FixedAmountVoucher(request.getDiscountType(), request.getDiscountValue());
            case PERCENT -> new PercentDiscountVoucher(request.getDiscountType(), request.getDiscountValue());
        };
    }
}
