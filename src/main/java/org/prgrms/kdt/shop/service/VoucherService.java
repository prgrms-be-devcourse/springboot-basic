package org.prgrms.kdt.shop.service;

import org.prgrms.kdt.shop.domain.Voucher;
import org.prgrms.kdt.shop.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Optional<Voucher> getVoucher(UUID voucherId) {
        return Optional.ofNullable(voucherRepository.findById(voucherId).orElseThrow(( ) -> new RuntimeException("Can not find a voucher for%s".formatted(voucherId))));
    }

    public Voucher insert(Voucher voucher) {
        voucherRepository.insert(voucher);
        logger.info("바우처 생성 -> {}", voucher);
        return voucher;
    }

    public void printAll( ) {
        List<Voucher> voucherList = voucherRepository.findByAll();
        if (voucherList.isEmpty()) {
            System.out.println("바우처가 없습니다.");
        } else {
            voucherList.forEach(s -> System.out.println("Voucher Id : " + s.getVoucherId() + "  Discount Info : " + s.getAmount() + " VoucherType : " + s.getVoucherType()));
        }
    }
}
