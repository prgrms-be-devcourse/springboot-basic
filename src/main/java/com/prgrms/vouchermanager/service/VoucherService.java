package com.prgrms.vouchermanager.service;

import com.prgrms.vouchermanager.domain.voucher.*;
import com.prgrms.vouchermanager.message.LogMessage;
import com.prgrms.vouchermanager.repository.voucher.VoucherRepository;
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

    public List<Voucher> findAll() {
        return voucherRepository.findAll();
    }

    public Voucher updateDiscount(UUID id, int discount) {
        Voucher voucher = voucherRepository.findById(id);
        Voucher updateVoucher = VoucherFactory.update(id,
                        VoucherTypeResolver.resolve(voucher),
                        discount)
                        .get();
        return voucherRepository.updateDiscount(updateVoucher);
    }

    public int delete(UUID id) {
        return voucherRepository.delete(id);
    }
}
