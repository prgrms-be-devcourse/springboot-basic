package com.example.springbootbasic.service.voucher;

import com.example.springbootbasic.domain.voucher.Voucher;
import com.example.springbootbasic.domain.voucher.VoucherType;
import com.example.springbootbasic.repository.voucher.JdbcVoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class JdbcVoucherService {
    private static final Logger logger = LoggerFactory.getLogger(JdbcVoucherService.class);
    private final JdbcVoucherRepository voucherRepository;

    public JdbcVoucherService(JdbcVoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher saveVoucher(Voucher voucher) {
        Voucher savedVoucher = voucherRepository.save(voucher);
        logger.info("Success - {}", savedVoucher.toString());
        return savedVoucher;
    }

    public List<Voucher> findAllVouchers() {
        logger.info("Success - findAllVouchers");
        return voucherRepository.findAllVouchers();
    }

    public List<Voucher> findAllVoucherByVoucherType(VoucherType voucherType) {
        logger.info("Success - findAllVoucherByVoucherType, voucherType =  {}", voucherType);
        return voucherRepository.findAllVouchersByVoucherType(voucherType);
    }

    public Voucher update(Voucher voucher) {
        logger.info("Success - update : {}", voucher.toString());
        return voucherRepository.update(voucher);
    }

    public void deleteAllVouchers() {
        logger.info("Success - deleteAllVouchers");
        voucherRepository.deleteAllVouchers();
    }

    public void deleteVouchersByVoucherType(VoucherType voucherType) {
        logger.error("Success - deleteVouchersByVoucherType");
        voucherRepository.deleteVouchersByVoucherType(voucherType);
    }

    public Voucher findById(long voucherId) {
        return voucherRepository.findById(voucherId);
    }
}
