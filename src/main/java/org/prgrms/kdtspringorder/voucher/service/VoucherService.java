package org.prgrms.kdtspringorder.voucher.service;

import org.prgrms.kdtspringorder.voucher.domain.Voucher;
import org.prgrms.kdtspringorder.voucher.enums.VoucherPolicy;
import org.prgrms.kdtspringorder.voucher.repository.abstraction.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final static Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;

    public VoucherService(
            VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher getVoucher(final UUID voucherId) {
        return this.voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException("Can not find a voucher for " + voucherId));
    }

    public List<Voucher> getVouchers() {
        logger.info("Voucher 정보 조회 시작");
        List<Voucher> vouchers = this.voucherRepository.getVouchers();
        logger.info("Voucher 정보 조회 종료");
        return vouchers;
    }

    public Voucher createVoucher(String voucherType) {
        logger.info("Voucher 생성 시작");

        VoucherPolicy policy = VoucherPolicy.of(voucherType);
        logger.info("Voucher Type : {}", policy.getPolicyType());

        Voucher voucher = new Voucher(policy);
        logger.info("Voucher 생성 완료");

        return this.voucherRepository.saveVoucher(voucher);
    }
}
