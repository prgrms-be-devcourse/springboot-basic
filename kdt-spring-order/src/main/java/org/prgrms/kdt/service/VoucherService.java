package org.prgrms.kdt.service;

import org.prgrms.kdt.Factory.VoucherFactory;
import org.prgrms.kdt.repository.voucher.VoucherRepository;
import org.prgrms.kdt.domain.voucher.Voucher;
import org.prgrms.kdt.service.dto.RequestCreatVoucherDto;
import org.prgrms.kdt.service.dto.RequestUpdateVoucherDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
public class VoucherService {
    private static final Logger logger = LoggerFactory.getLogger(VoucherService.class);
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public Voucher insert(RequestCreatVoucherDto dto) {
        UUID voucherId = UUID.randomUUID();
        return voucherRepository.insert(VoucherFactory.createVoucher(voucherId, dto.getType(), dto.getValue(), LocalDateTime.now()));
    }

    public Voucher findById(UUID voucherId) {
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

    @Transactional
    public Voucher update(RequestUpdateVoucherDto updateDto) {
        Voucher voucher = voucherRepository.findById(updateDto.getVoucherId())
                .orElseThrow(() -> new IllegalArgumentException("Can not find a voucher for " + updateDto.getVoucherId()));

        return voucherRepository.update(voucher.changeValue(updateDto.getValue()));
    }

    @Transactional
    public void deleteById(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }

    @Transactional
    public void deleteAll() {
        voucherRepository.deleteAll();
    }
}
