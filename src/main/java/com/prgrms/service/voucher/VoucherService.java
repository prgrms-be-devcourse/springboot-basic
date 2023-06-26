package com.prgrms.service.voucher;

import com.prgrms.model.dto.VoucherResponse;
import com.prgrms.model.voucher.*;
import com.prgrms.repository.voucher.VoucherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.DigestInputStream;
import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("can not find a voucher for {0}", voucherId)));
    }

    public Voucher createVoucher(VoucherPolicy voucherPolicy, Discount discount) {
        Voucher voucher = null;
        UUID id = UUID.randomUUID();

        switch (voucherPolicy) {
            case FixedAmountVoucher -> voucher = new FixedAmountVoucher(id, discount, voucherPolicy);
            case PercentDiscountVoucher -> voucher = new PercentDiscountVoucher(id, discount, voucherPolicy);
        }

        voucherRepository.insert(voucher);
        return voucher;
    }

    public List<VoucherResponse> getAllVoucherList() {
        return voucherRepository.getAllVoucherList().stream()
                .map(VoucherResponse::of)
                .collect(Collectors.toList());
    }

    public boolean isEmptyRepository(List<VoucherResponse> voucherList) {
        return voucherList.size() == 0;
    }
}
