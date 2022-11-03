package org.programmers.springbootbasic.service;

import lombok.AllArgsConstructor;
import org.programmers.springbootbasic.data.VoucherType;
import org.programmers.springbootbasic.domain.Voucher;
import org.programmers.springbootbasic.domain.VoucherFactory;
import org.programmers.springbootbasic.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherFactory voucherFactory;

    public Voucher createVoucher(VoucherType type, long amount) {
        Voucher voucher = voucherFactory.getVoucher(type, amount);
        return voucherRepository.save(voucher);
    }

    public List<Voucher> lookupVoucher() {
        return voucherRepository.findAll();
    }

}
