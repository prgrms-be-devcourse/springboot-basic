package com.prgrms.voucher_manager.voucher.service;

import com.prgrms.voucher_manager.voucher.Voucher;
import com.prgrms.voucher_manager.voucher.VoucherType;
import com.prgrms.voucher_manager.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;


@Service
public class VoucherService {
    private final static Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<Voucher> getFindAllVoucher() {
        List<Voucher> vouchers = new ArrayList<>();
        try{
            vouchers = voucherRepository.findAll();
            AtomicInteger i = new AtomicInteger();
            vouchers.forEach(v -> {
                logger.info(i.getAndIncrement() + " : " + v.toString());
            });
        } catch (RuntimeException e) {
            logger.info("JDBC voucher 가 비어있습니다. ");
        }
        return vouchers;
    }

    public Voucher createVoucher(String type, Long value) {
        VoucherType voucherType = VoucherType.getVoucherType(type);
        Voucher voucher = voucherType.create(value);
        voucherRepository.insert(voucher);
        return voucher;
    }
    public List<Voucher> findByType(String type) {
        return voucherRepository.findByType(type);
    }

    public List<Voucher> findByDate(LocalDate start, LocalDate end) {
        return voucherRepository.findByDate(start, end);
    }

    public List<Voucher> findByDateAndType(LocalDate start, LocalDate end, String type) {
        return voucherRepository.findByDateAndType(start, end, type);
    }

    public Voucher findById(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException());
    }

    public void deleteVoucher(Voucher voucher) {
        voucherRepository.delete(voucher);
    }

    public void updateVoucher(Voucher updateVoucher) {
        voucherRepository.update(updateVoucher);
    }
}
