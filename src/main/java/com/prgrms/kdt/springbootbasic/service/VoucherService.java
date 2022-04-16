package com.prgrms.kdt.springbootbasic.service;

import com.prgrms.kdt.springbootbasic.entity.Voucher;
import com.prgrms.kdt.springbootbasic.outputPackage.ConsoleOutput;
import com.prgrms.kdt.springbootbasic.repository.FileVoucherRepository;
import com.prgrms.kdt.springbootbasic.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VoucherService {
    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    private static VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository){
        this.voucherRepository = voucherRepository;
    }

    public Voucher saveVoucher(Voucher voucher) throws Exception {
        boolean checkDuplication = checkVoucherIdDuplication(voucher);
        if (checkDuplication == true) {
            logger.info("[VoucherService : saveVoucher] Voucher is saved");
            return voucherRepository.saveVoucher(voucher);
        }else{
            logger.error("[VoucherService : saveVoucher] Error occurred while saving voucher");
            throw new Exception("Voucher ID Duplicated");
        }
    }

    public boolean checkVoucherIdDuplication(Voucher voucher){
        Voucher findVoucher = voucherRepository.findById(voucher.getVoucherId());
        if (findVoucher == null){
            logger.info("[VoucherService : checkVoucherIdDuplication] Voucher's Id is unique");
            return true;
        }else{
            logger.error("[VoucherService : checkVoucherIdDuplication] Voucher's Id is not unique");
            return false;
        }
    }

    public List<Voucher> getAllVouchers() {
        return voucherRepository.getAllVouchers();
    }

}
