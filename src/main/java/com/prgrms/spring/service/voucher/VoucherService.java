package com.prgrms.spring.service.voucher;

import com.prgrms.spring.domain.voucher.FixedAmountVoucher;
import com.prgrms.spring.domain.voucher.PercentDiscountVoucher;
import com.prgrms.spring.domain.voucher.Voucher;
import com.prgrms.spring.domain.voucher.VoucherType;
import com.prgrms.spring.repository.voucher.VoucherRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@AllArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;

    @Transactional
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

    @Transactional(readOnly = true)
    public List<String> getAllVoucher() {
        List<Voucher> voucherList =  voucherRepository.findAll();
        List<String> outputList = new ArrayList<>();
        voucherList.forEach(voucher -> outputList.add(String.format("%s : %d %s", voucher.getVoucherName(), voucher.getDiscount(), voucher.getDiscountUnit())));
        return outputList;
    }
}
