package org.prgrms.kdtspringdemo.voucher.service;

import org.prgrms.kdtspringdemo.dto.VoucherRequestDto;
import org.prgrms.kdtspringdemo.dto.VoucherViewDto;
import org.prgrms.kdtspringdemo.voucher.domain.Voucher;
import org.prgrms.kdtspringdemo.voucher.domain.VoucherTypeFunction;
import org.prgrms.kdtspringdemo.voucher.repository.VoucherRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final Logger logger = LoggerFactory.getLogger(VoucherService.class);

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherTypeFunction getVoucherTypeFunction(String type) {
        return VoucherTypeFunction.findByCode(type);
    }

    public VoucherViewDto createVoucher(VoucherTypeFunction voucherType, long amount) {
        UUID voucherId = UUID.randomUUID();
        Voucher voucher = voucherRepository.insert(voucherType.create(voucherId, amount));
        return new VoucherViewDto(voucher);
    }

    public void updateVoucher(UUID voucherId, VoucherRequestDto voucherRequestDto) {
        voucherRepository.update(voucherId, voucherRequestDto);
    }

    public List<VoucherViewDto> getVoucherViewDtoList() {
        return voucherRepository.findAll().stream().map(VoucherViewDto::new).toList();
    }

    public List<VoucherViewDto> findByPolicy(String policy) {
        return voucherRepository.findByPolicy(policy).stream().map(VoucherViewDto::new).toList();
    }

    public List<VoucherViewDto> findUnallocatedVoucher() {
        return voucherRepository.findUnallocatedVoucher().stream().map(VoucherViewDto::new).toList();
    }

    public VoucherViewDto findById(UUID voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId).orElseThrow(() -> {
            logger.error(MessageFormat.format("Can not find a voucher for {0}", voucherId));
            return new RuntimeException(MessageFormat.format("Can not find a voucher for {0}", voucherId));
        });
        return new VoucherViewDto(voucher);
    }

    public void deleteById(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }

    public void deleteAll() {
        voucherRepository.deleteAll();
    }
}
