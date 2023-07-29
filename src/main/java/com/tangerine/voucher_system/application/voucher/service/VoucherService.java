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

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherServiceMapper mapper;

    public VoucherService(VoucherRepository voucherRepository, VoucherServiceMapper mapper) {
        this.voucherRepository = voucherRepository;
        this.mapper = mapper;
    }

    public VoucherResult createVoucher(VoucherParam param) {
        return mapper.domainToResult(
                voucherRepository.insert(mapper.paramToDomain(param)));
    }

    public VoucherResult updateVoucher(VoucherParam param) {
        return mapper.domainToResult(
                voucherRepository.update(mapper.paramToDomain(param))
        );
    }

    public List<VoucherResult> findVouchers() {
        return mapper.domainsToResults(voucherRepository.findAll());
    }

    public VoucherResult findVoucherById(UUID voucherId) {
        return mapper.domainToResult(
                voucherRepository.findById(voucherId)
                        .orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText()))
        );
    }

    public List<VoucherResult> findVoucherByCreatedAt(LocalDate createdAt) {
        return mapper.domainsToResults(voucherRepository.findByCreatedAt(createdAt));
    }

    public VoucherResult deleteVoucherById(UUID voucherId) {
        Optional<Voucher> foundVoucher = voucherRepository.findById(voucherId);
        voucherRepository.deleteById(voucherId);
        return mapper.domainToResult(
                foundVoucher.orElseThrow(() -> new InvalidDataException(ErrorMessage.INVALID_PROPERTY.getMessageText())));
    }

}