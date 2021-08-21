package org.prgrms.kdt.service;

import org.prgrms.kdt.Factory.VoucherFactory;
import org.prgrms.kdt.domain.voucher.VoucherType;
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
        voucherRepository.insert(VoucherFactory.createVoucher(voucherId, dto.getType(), dto.getAmount()));
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
