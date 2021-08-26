package com.programmers.voucher.service.voucher;

import com.programmers.voucher.entity.voucher.DiscountPolicy;
import com.programmers.voucher.entity.voucher.Voucher;
import com.programmers.voucher.repository.voucher.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasicVoucherService implements VoucherService {

    private static final Logger log = LoggerFactory.getLogger(BasicVoucherService.class);

    private final VoucherRepository voucherRepository;

    public BasicVoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Override
    public void openStorage() {
        voucherRepository.loadVouchers();
    }

    @Override
    public void closeStorage() {
        voucherRepository.persistVouchers();
    }

    @Override
    public Voucher create(String name, DiscountPolicy.Type type, int value) {
        Voucher voucher = new Voucher(name, new DiscountPolicy(value, type));
        voucher = voucherRepository.save(voucher);
        log.debug("Persisted voucher {} to repository", voucher.toString());
        return voucher;
    }

    @Override
    public List<Voucher> listAll() {
        return voucherRepository.listAll();
    }
}
