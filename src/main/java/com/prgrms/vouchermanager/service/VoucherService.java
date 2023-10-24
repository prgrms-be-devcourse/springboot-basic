package com.prgrms.vouchermanager.service;

import com.prgrms.vouchermanager.domain.Customer;
import com.prgrms.vouchermanager.domain.Voucher;
import com.prgrms.vouchermanager.domain.VoucherFactory;
import com.prgrms.vouchermanager.io.VoucherType;
import com.prgrms.vouchermanager.message.LogMessage;
import com.prgrms.vouchermanager.repository.BlacklistRepository;
import com.prgrms.vouchermanager.repository.VoucherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
