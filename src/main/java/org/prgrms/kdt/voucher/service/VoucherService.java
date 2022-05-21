package org.prgrms.kdt.voucher.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.model.VoucherType;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class VoucherService {

    private static final Logger log = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher makeVoucher(VoucherType voucherMenu, long discountValue) {
        log.info("바우처를 생성합니다. => [타입 : {}] [값 : {}]", voucherMenu, discountValue);
        Voucher voucher = voucherMenu.create(discountValue);
        voucherRepository.save(voucher);
        return voucher;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository.findById(voucherId)
            .orElseThrow(NoSuchElementException::new);
    }

    public List<Voucher> getVouchers() {
        return voucherRepository.findAll();
    }

    public void deleteVouchers() {
        voucherRepository.deleteAll();
    }

}
