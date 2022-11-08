package org.prgrms.vouchermanagement.voucher.service;

import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.prgrms.vouchermanagement.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

@Service
public class VoucherListFindService {

    private final VoucherRepository voucherRepository;

    private final Logger logger = getLogger(VoucherListFindService.class);

    @Autowired
    public VoucherListFindService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public List<Voucher> findAllVouchers() {

        logger.info("바우처 리스트 출력");

        return voucherRepository.findAll();
    }
}
