package com.prgrms.springbasic.domain.voucher.service;

import com.prgrms.springbasic.domain.voucher.dto.CreateVoucherRequest;
import com.prgrms.springbasic.domain.voucher.dto.UpdateVoucherRequest;
import com.prgrms.springbasic.domain.voucher.dto.VoucherResponse;
import com.prgrms.springbasic.domain.voucher.entity.DiscountType;
import com.prgrms.springbasic.domain.voucher.entity.FixedAmountVoucher;
import com.prgrms.springbasic.domain.voucher.entity.PercentDiscountVoucher;
import com.prgrms.springbasic.domain.voucher.entity.Voucher;
import com.prgrms.springbasic.domain.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
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

    public VoucherResponse findVoucherById(UUID voucherId) {
        Voucher voucher = voucherRepository.findVoucherById(voucherId)
                .orElseThrow(() -> new IllegalArgumentException("Voucher not found"));
        return VoucherResponse.from(voucher);
    }

    public void updateVoucher(UpdateVoucherRequest request) {
        Voucher voucher = voucherRepository.findVoucherById(request.voucher_id())
                .orElseThrow(() -> new IllegalArgumentException("Voucher not found"));
        voucher.update(request.discountValue());
        voucherRepository.updateVoucher(voucher);
    }

    public void deleteAll() {
        voucherRepository.deleteAll();
    }

    private Voucher createVoucher(CreateVoucherRequest request) {
        return switch (DiscountType.find(request.discountType())) {
            case FIXED -> FixedAmountVoucher.create(UUID.randomUUID(), request.discountType(), request.discountValue());
            case PERCENT -> PercentDiscountVoucher.create(UUID.randomUUID(), request.discountType(), request.discountValue());
        };
    }
}
