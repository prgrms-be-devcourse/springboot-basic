package com.wonu606.vouchermanager.service.voucher;

import com.wonu606.vouchermanager.domain.voucher.Voucher;
import com.wonu606.vouchermanager.domain.voucher.VoucherDto;
import com.wonu606.vouchermanager.repository.voucher.VoucherRepository;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@Transactional
public class VoucherService {

    private static final Logger log = LoggerFactory.getLogger(VoucherService.class);
    private static final int MAX_RETRIES = 3;

    private final VoucherFactory factory;
    private final VoucherRepository voucherRepository;


    public VoucherService(VoucherFactory factory, VoucherRepository voucherRepository) {
        this.factory = factory;
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherDto voucherDto) {
        return createVoucher(voucherDto, 0);
    }

    private Voucher createVoucher(VoucherDto voucherDto, int retryCount) {
        if (retryCount > MAX_RETRIES) {
            log.warn("uuid를 재성성하여 저장하는 시도 최대 횟수 {}를 초과하였습니다. ", MAX_RETRIES);
            throw new IllegalStateException();
        }

        Voucher voucher = factory.create(voucherDto);
        try {
            return voucherRepository.save(voucher);
        } catch (DuplicateKeyException e) {
            log.info("DuplicateKeyException가 발생하였습니다. ", e);
            return createVoucher(voucherDto, retryCount + 1);
        }
    }

    public List<Voucher> getVoucherList() {
        return voucherRepository.findAll();
    }
}

