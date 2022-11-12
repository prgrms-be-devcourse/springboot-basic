package org.prgrms.kdt.service;

import org.prgrms.kdt.domain.Voucher;
import org.prgrms.kdt.repository.VoucherRepository;
import org.prgrms.kdt.voucher.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

@Service
public class VoucherService {
    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<Voucher> getAllVoucher() {
        logger.info("AllVoucher()-> 모든 바우처 출력");
        return voucherRepository.findAll();
    }

    public Voucher createVoucher(String type, long discountDegree) {
        logger.info(MessageFormat.format("createVoucher()->{1}Voucher 생성", type));
        Voucher voucher = VoucherType.createVoucher(type, discountDegree);
        voucherRepository.insert(voucher);
        return voucher;
    }
}
