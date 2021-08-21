package com.programmers.voucher.service.voucher;

import com.programmers.voucher.entity.voucher.Voucher;
import com.programmers.voucher.entity.voucher.factory.VoucherFactory;
import com.programmers.voucher.repository.voucher.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BasicVoucherService implements VoucherService {

    private static final Logger log = LoggerFactory.getLogger(BasicVoucherService.class);

    private final VoucherRepository voucherRepository;
    private final VoucherFactory fixedAmountVoucherFactory;
    private final VoucherFactory percentDiscountVoucherFactory;

    public BasicVoucherService(VoucherRepository voucherRepository, VoucherFactory fixedAmountVoucherFactory,
            VoucherFactory percentDiscountVoucherFactory) {
        this.voucherRepository = voucherRepository;
        this.fixedAmountVoucherFactory = fixedAmountVoucherFactory;
        this.percentDiscountVoucherFactory = percentDiscountVoucherFactory;
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
    public Voucher create(String name, Voucher.type type, double value) {
        Voucher voucher;
        switch (type) {
            case FIXED:
                voucher = fixedAmountVoucherFactory.create(name, value);
                break;

            case PERCENT:
                voucher = percentDiscountVoucherFactory.create(name, value);
                break;

            default:
                voucher = fixedAmountVoucherFactory.create(name, value);
        }

        log.debug("Created voucher {}", voucher.toString());
        return voucherRepository.save(voucher);
    }

    @Override
    public List<Voucher> listAll() {
        return voucherRepository.listAll();
    }
}
