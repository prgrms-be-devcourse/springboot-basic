package prgms.spring_week1.domain.voucher.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import prgms.spring_week1.domain.voucher.model.Voucher;

import prgms.spring_week1.domain.voucher.model.type.VoucherType;
import prgms.spring_week1.domain.voucher.repository.VoucherRepository;
import prgms.spring_week1.io.Input;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public void insertNewVoucher(VoucherType voucherType, int discountAmount) {
        Voucher voucher = new Voucher(voucherType, discountAmount);
        voucherRepository.insert(voucher);
    }

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public List<Voucher> findByType(String voucherType) {
        return voucherRepository.findByType(voucherType);
    }

    public void deleteAll(VoucherType voucherType) {
        voucherRepository.delete();
    }
}
