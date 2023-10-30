package com.prgrms.vouchermanager.service;

import com.prgrms.vouchermanager.domain.voucher.*;
import com.prgrms.vouchermanager.message.LogMessage;
import com.prgrms.vouchermanager.repository.voucher.VoucherRepository;
import com.prgrms.vouchermanager.util.VoucherFactory;
import com.prgrms.vouchermanager.util.VoucherTypeResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class VoucherService {

    private final VoucherRepository voucherRepository;
    @Autowired
    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
        log.info(LogMessage.CHECK_VOUCHER_REPOSITORY.getMessage(), voucherRepository.getClass());
    }

    public Voucher create(VoucherType voucherType, long discount) {
        Voucher voucher = VoucherFactory.create(voucherType, discount).get();
        log.info(LogMessage.VOUCHER_INFO.getMessage(), voucher);

        voucherRepository.create(voucher);
        return voucher;
    }

    public Voucher findById(UUID id) {
        return voucherRepository.findById(id);
    }
    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public Voucher updateDiscount(UUID id, int discount) {
        Voucher voucher = voucherRepository.findById(id);
        Voucher updateVoucher = VoucherFactory.update(id,
                        voucher.getType(),
                        discount)
                        .get();
        return voucherRepository.updateDiscount(updateVoucher);
    }

    public int delete(UUID id) {
        return voucherRepository.delete(id);
    }

    public List<Voucher> findByCondition(VoucherType voucherType, int startYear, int startMonth, int endYear, int endMonth) {
        List<Voucher> byDate
                = voucherRepository.findByDate(startYear, startMonth, endYear, endMonth);
        List<Voucher> byType =
                voucherRepository.findByVoucherType(voucherType);
        byDate.retainAll(byType);
        return byDate;
    }
}
