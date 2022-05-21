package org.prgrms.kdt.voucher.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import org.prgrms.kdt.error.VoucherNotFoundException;
import org.prgrms.kdt.voucher.model.Voucher;
import org.prgrms.kdt.voucher.model.VoucherType;
import org.prgrms.kdt.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class VoucherService {

    private static final Logger log = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public Voucher makeVoucher(VoucherType voucherMenu, long discountValue) {
        log.info("바우처를 생성합니다. => [타입 : {}] [값 : {}]", voucherMenu, discountValue);
        Voucher voucher = voucherMenu.create(discountValue);
        voucherRepository.save(voucher);
        return voucher;
    }

    @Transactional
    public void updateVoucher(UUID id, long value) {
        voucherRepository.update(id, value);
    }

    public Voucher getVoucher(UUID id) {
        return voucherRepository.findById(id)
            .orElseThrow(VoucherNotFoundException::new);
    }

    public List<Voucher> getVouchers() {
        return voucherRepository.findAll();
    }

    @Transactional
    public void deleteVoucher(UUID id) {
        voucherRepository.deleteById(id);
    }

    @Transactional
    public void deleteVouchers() {
        voucherRepository.deleteAll();
    }

}
