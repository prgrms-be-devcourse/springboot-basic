package com.prgrms.springbasic.domain.voucher.service;

import com.prgrms.springbasic.domain.voucher.entity.FixedAmountVoucher;
import com.prgrms.springbasic.domain.voucher.entity.Voucher;
import com.prgrms.springbasic.domain.voucher.entity.DiscountType;
import com.prgrms.springbasic.domain.voucher.entity.PercentDiscountVoucher;
import com.prgrms.springbasic.domain.voucher.dto.CreateVoucherRequest;
import com.prgrms.springbasic.domain.voucher.dto.VoucherResponse;
import com.prgrms.springbasic.domain.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {

    private final Logger logger = LoggerFactory.getLogger(VoucherService.class);

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
