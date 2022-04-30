package org.devcourse.voucher.voucher.service;

import org.devcourse.voucher.voucher.model.Voucher;
import org.devcourse.voucher.voucher.model.VoucherType;
import org.devcourse.voucher.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class VoucherServiceImpl implements VoucherService{
    private final VoucherRepository voucherRepository;
    private final Logger logger = LoggerFactory.getLogger(VoucherServiceImpl.class);

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherType voucherType, long discount) {
        logger.info("Service : Create Voucher");
        Voucher voucher = voucherType.voucherCreator(UUID.randomUUID(), discount);
        return voucherRepository.insert(voucher);
    }

    @Override
    public List<Voucher> recallAllVoucher() {
        logger.info("Service : Voucher Inquiry");
        return voucherRepository.findAll();
    }

}
