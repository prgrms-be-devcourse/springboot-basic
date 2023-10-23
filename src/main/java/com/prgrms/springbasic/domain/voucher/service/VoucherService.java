package com.prgrms.springbasic.domain.voucher.service;

import com.prgrms.springbasic.domain.voucher.dto.CreateVoucherRequest;
import com.prgrms.springbasic.domain.voucher.dto.VoucherResponse;
import com.prgrms.springbasic.domain.voucher.entity.DiscountType;
import com.prgrms.springbasic.domain.voucher.entity.FixedAmountVoucher;
import com.prgrms.springbasic.domain.voucher.entity.PercentDiscountVoucher;
import com.prgrms.springbasic.domain.voucher.entity.Voucher;
import com.prgrms.springbasic.domain.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherResponse saveVoucher(CreateVoucherRequest request) {
        logger.info("Voucher service saveVoucher run.. Discount Type : {}, Discount Value : {}", request.getDiscountType(), request.getDiscountValue());

        Voucher voucher = voucherRepository.saveVoucher(createVoucher(request));
        return VoucherResponse.from(voucher);
    }

    public List<VoucherResponse> findAll() {
        logger.info("Voucher service findAll run..");

        return voucherRepository.findAll().stream()
                .map(VoucherResponse::from)
                .toList();
    }

    private Voucher createVoucher(CreateVoucherRequest request) {
        return switch (DiscountType.find(request.getDiscountType())) {
            case FIXED -> FixedAmountVoucher.create(UUID.randomUUID(), request.getDiscountType(), request.getDiscountValue());
            case PERCENT -> PercentDiscountVoucher.create(UUID.randomUUID(), request.getDiscountType(), request.getDiscountValue());
        };
    }
}
