package org.devcourse.voucher.voucher.service;

import org.devcourse.voucher.customer.repository.BlacklistRepository;
import org.devcourse.voucher.model.ListType;
import org.devcourse.voucher.voucher.model.Voucher;
import org.devcourse.voucher.voucher.model.VoucherType;
import org.devcourse.voucher.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@Service
public class VoucherServiceImpl implements VoucherService{
    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher createVoucher(VoucherType voucherType, long discount) {
        Voucher voucher = voucherType.voucherCreator(UUID.randomUUID(), discount);
        return voucherRepository.insert(voucher);
    }

    @Override
    public List<Voucher> recallAllVoucher() {
        return voucherRepository.findAll();
    }

}
