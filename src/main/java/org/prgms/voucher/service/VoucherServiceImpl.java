package org.prgms.voucher.service;

import lombok.RequiredArgsConstructor;
import org.prgms.voucher.repository.VoucherRepository;
import org.prgms.voucher.voucher.Voucher;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;

    @Override
    public Voucher findVoucher(long id) {
        return voucherRepository.findById(id).orElseThrow(() -> new NoSuchElementException("id에 해당하는 바우처가 존재하지 않습니다."));
    }

    @Override
    public Voucher create(Voucher voucher) {
        return this.voucherRepository.save(voucher);
    }

    @Override
    public List<Voucher> list() {
        return this.voucherRepository.findAll();
    }
}
