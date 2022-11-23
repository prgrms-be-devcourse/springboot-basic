package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<Voucher> getAllVoucher() {
        return voucherRepository.findAll();
    }

    public Voucher createVoucher(String type, long discountDegree) {
        return voucherRepository.insert(type, discountDegree);
    }

    public void updateVoucher(long voucherId, long discountDegree) {
        voucherRepository.update(voucherId, discountDegree);
    }

    public Voucher findById(long voucherId) {
        return voucherRepository.findById(voucherId);
    }

    public void deleteAll() {
        voucherRepository.deleteAll();
    }

    public void deleteById(long voucherId){
        voucherRepository.deleteById(voucherId);
    }

}
