package prgms.spring_week1.domain.voucher.service;

import org.springframework.stereotype.Service;
import prgms.spring_week1.domain.voucher.model.Voucher;

import prgms.spring_week1.domain.voucher.model.type.VoucherType;
import prgms.spring_week1.domain.voucher.repository.VoucherRepository;

import java.util.List;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void insertNewVoucher(VoucherType voucherType, int discountAmount) {
        switch (voucherType) {
            case FIXED -> voucherRepository.insert(new Voucher(VoucherType.FIXED,discountAmount));
            case PERCENT -> voucherRepository.insert(new Voucher(VoucherType.PERCENT,discountAmount));
        }
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

}
