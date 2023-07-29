package com.tangerine.voucher_system.application.voucher.service;

import com.tangerine.voucher_system.application.global.exception.ErrorMessage;
import com.tangerine.voucher_system.application.global.exception.InvalidDataException;
import com.tangerine.voucher_system.application.voucher.model.Voucher;
import com.tangerine.voucher_system.application.voucher.repository.VoucherRepository;
import com.tangerine.voucher_system.application.voucher.service.dto.VoucherParam;
import com.tangerine.voucher_system.application.voucher.service.dto.VoucherResult;
import com.tangerine.voucher_system.application.voucher.service.mapper.VoucherServiceMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherResult createVoucher(VoucherParam param) {
        return VoucherServiceMapper.INSTANCE.domainToResult(
                voucherRepository.insert(VoucherServiceMapper.INSTANCE.paramToDomain(param)));
    }

    public VoucherResult updateVoucher(VoucherParam param) {
        return VoucherServiceMapper.INSTANCE.domainToResult(
                voucherRepository.update(VoucherServiceMapper.INSTANCE.paramToDomain(param))
        );
    }

    public List<VoucherResult> findVouchers() {
        return VoucherServiceMapper.INSTANCE.domainsToResults(voucherRepository.findAll());
    }

    public VoucherResult findVoucherById(UUID voucherId) {
        return VoucherServiceMapper.INSTANCE.domainToResult(
                voucherRepository.findById(voucherId)
                        .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText()))
        );
    }

    public List<VoucherResult> findVoucherByCreatedAt(LocalDate createdAt) {
        return VoucherServiceMapper.INSTANCE.domainsToResults(voucherRepository.findByCreatedAt(createdAt));
    }

    public VoucherResult deleteVoucherById(UUID voucherId) {
        Optional<Voucher> foundVoucher = voucherRepository.findById(voucherId);
        voucherRepository.deleteById(voucherId);
        return VoucherServiceMapper.INSTANCE.domainToResult(
                foundVoucher.orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText())));
    }

}