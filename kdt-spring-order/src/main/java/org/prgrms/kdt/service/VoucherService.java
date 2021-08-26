package org.prgrms.kdt.service;

import org.prgrms.kdt.Factory.VoucherFactory;
import org.prgrms.kdt.repository.voucher.VoucherRepository;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.service.dto.RequestCreatVoucherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);
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
                .orElseThrow(() -> {
                        RuntimeException exception = new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId));
                        logger.error("Exception at {0}: {1}", this.getClass().getSimpleName(), exception.getMessage());
                        return exception;
                    }
                );

    }

    public List<Voucher> vouchers() {
        return voucherRepository.findAll();
    }

    public void useVoucher(Voucher voucher) {

    }
}
