package org.prgms.voucher.service;

import lombok.RequiredArgsConstructor;
import org.prgms.voucher.repository.VoucherRepository;
import org.prgms.voucher.voucher.Voucher;
import org.prgms.voucher.voucher.VoucherFactory;
import org.prgms.voucher.voucher.VoucherPolicy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherFactory voucherFactory;

    public Voucher findVoucher(long id) {
        return voucherRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("id에 해당하는 바우처가 존재하지 않습니다."));
    }

    public Voucher create(VoucherPolicy voucherPolicy, long amount) {
        Voucher voucher = voucherFactory.createVoucher(voucherPolicy, amount);
        return this.voucherRepository.save(voucher);
    }

    public List<Voucher> getVoucherList() {
        return this.voucherRepository.findAll();
    }
}
