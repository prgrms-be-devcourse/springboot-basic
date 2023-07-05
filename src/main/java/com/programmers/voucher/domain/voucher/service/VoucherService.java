package com.programmers.voucher.domain.voucher.service;

import com.programmers.voucher.domain.voucher.domain.Voucher;
import com.programmers.voucher.domain.voucher.domain.VoucherType;
import com.programmers.voucher.domain.voucher.repository.VoucherRepository;
import com.programmers.voucher.domain.voucher.util.VoucherMessages;
import com.programmers.voucher.global.util.DataErrorMessages;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import static com.programmers.voucher.domain.voucher.util.VoucherMessages.CREATED_NEW_VOUCHER;

@Service
public class VoucherService {
    private static final Logger LOG = LoggerFactory.getLogger(VoucherService.class);

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public UUID createVoucher(VoucherType voucherType, long amount) {
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = voucherType.createVoucher(voucherId, amount);

        voucherRepository.save(voucher);

        LOG.info(CREATED_NEW_VOUCHER, voucher.toString());
        return voucherId;
    }

    public List<Voucher> findVouchers() {
        return voucherRepository.findAll();
    }

    public void deleteVoucher(UUID voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new NoSuchElementException(DataErrorMessages.NO_SUCH_ELEMENT));

        voucherRepository.deleteById(voucherId);
        LOG.info(VoucherMessages.DELETE_VOUCHER, voucher);
    }
}
