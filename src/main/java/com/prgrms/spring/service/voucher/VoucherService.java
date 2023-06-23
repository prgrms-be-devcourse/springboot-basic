package com.prgrms.spring.service.voucher;

import com.prgrms.spring.domain.voucher.FixedAmountVoucher;
import com.prgrms.spring.domain.voucher.PercentDiscountVoucher;
import com.prgrms.spring.domain.voucher.Voucher;
import com.prgrms.spring.domain.voucher.VoucherType;
import com.prgrms.spring.infrastructure.voucher.VoucherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class VoucherService {

    private VoucherRepository voucherRepository;

    public Voucher createVoucher(VoucherType type, Long discount) {
        Voucher voucher = null;
        if (type == VoucherType.FIXED_AMOUNT) {
            voucher = FixedAmountVoucher.newInstance(UUID.randomUUID(), discount);
        } else if (type == VoucherType.PERCENT_DISCOUNT) {
            voucher = PercentDiscountVoucher.newInstance(UUID.randomUUID(), discount);
        }
        voucherRepository.insert(voucher);
        return voucher;
    }

    public List<String> getAllVoucher() {
        Map<UUID, Voucher> vouchers = voucherRepository.findAll();
        List<Voucher> voucherList = new ArrayList<>(vouchers.values());
        List<String> outputList = new ArrayList<>();

        for (Voucher voucher: voucherList) {
            if (voucher instanceof FixedAmountVoucher) {
                outputList.add(String.format("FixedAmountVoucher : %d $", voucher.getDiscount()));
            } else if (voucher instanceof  PercentDiscountVoucher) {
                outputList.add(String.format("PercentDiscountVoucher : %d %%", voucher.getDiscount()));
            }
        }
        return outputList;
    }
}
