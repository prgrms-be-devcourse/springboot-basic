package org.prgrms.kdt.service;

import org.prgrms.kdt.domain.voucher.FixedAmountVoucher;
import org.prgrms.kdt.domain.voucher.PercentAmountVoucher;
import org.prgrms.kdt.repository.VoucherRepository;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.service.dto.RequestCreatVoucherDto;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public UUID save(RequestCreatVoucherDto dto) {
        UUID voucherId = UUID.randomUUID();
        if (dto.getType() == 0) { // fixed
            voucherRepository.insert(new FixedAmountVoucher(voucherId, dto.getAmount()));
        } else { // percent
            voucherRepository.insert(new PercentAmountVoucher(voucherId, dto.getAmount()));
        }

        return voucherId;
    }

    public Voucher getVoucher(UUID voucherId) {
        return voucherRepository
                .findById(voucherId)
                .orElseThrow(() -> new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId)));

    }

    public List<Voucher> vouchers() {
        return voucherRepository.findAll();
    }

    public void useVoucher(Voucher voucher) {

    }
}
