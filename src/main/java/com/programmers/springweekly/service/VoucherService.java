package com.programmers.springweekly.service;

import com.programmers.springweekly.domain.voucher.Voucher;
import com.programmers.springweekly.domain.voucher.VoucherFactory;
import com.programmers.springweekly.domain.voucher.VoucherType;
import com.programmers.springweekly.repository.voucher.VoucherRepository;
import java.util.Map;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public void saveVoucher(VoucherType voucherType, String discount) {
        Voucher voucher = VoucherFactory.createVoucher(voucherType, discount);
        voucherRepository.save(voucher);
    }

    public Map<UUID, Voucher> findVoucherAll() {
        return voucherRepository.getList();
    }
}
