package org.programmers.voucher.service;

import org.programmers.voucher.domain.Voucher;
import org.programmers.voucher.domain.VoucherType;
import org.programmers.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {
    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;

    private long amount;
    private long percent;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<Voucher> listVoucher(){
        logger.info("list voucher");
        return voucherRepository.findAll();
    }

    public void makeVoucher(VoucherType voucherType, Long value){
        logger.info("make voucher");
        Voucher voucher = VoucherType.makeVoucher(voucherType, value);
        logger.debug("voucher => {}", voucher.toString());
        logger.info("voucher save start");
        voucherRepository.save(voucher);
        logger.info("voucher save success");
    }
}
