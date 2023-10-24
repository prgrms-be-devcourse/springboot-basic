package com.prgrms.vouchermanager.service;

import com.prgrms.vouchermanager.domain.voucher.Voucher;
import com.prgrms.vouchermanager.domain.voucher.VoucherFactory;
import com.prgrms.vouchermanager.domain.voucher.VoucherType;
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

    public List<Voucher> list() {
        return voucherRepository.list();
    }

    public void updateDiscount(UUID id, int discount) {
        voucherRepository.updateDiscount(id, discount);
    }

    public UUID delete(UUID id) {
        voucherRepository.delete(id);
        return id;
    }
}
