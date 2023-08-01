package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.global.exception.EntityNotFoundException;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.dao.VoucherRepository;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.service.dto.*;
import org.prgrms.kdt.voucher.service.mapper.ServiceVoucherMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional(readOnly = true)
@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final ServiceVoucherMapper mapper;

    public VoucherService(VoucherRepository voucherRepository, ServiceVoucherMapper mapper) {
        this.voucherRepository = voucherRepository;
        this.mapper = mapper;
    }

    @Transactional
    public VoucherResponse createVoucher(CreateVoucherRequest request) {
        Voucher voucher = voucherRepository.insert(mapper.convertVoucher(request));
        return new VoucherResponse(voucher);
    }

    public VoucherResponses findAll(SearchRequest request) {
        return VoucherResponses.of(voucherRepository.findAll(request));
    }

    public VoucherDetailResponse findById(UUID id) {
        return new VoucherDetailResponse(voucherRepository.findById(id).orElseThrow(EntityNotFoundException::new));
    }

    @Transactional
    public void deleteById(UUID id) {
        voucherRepository.deleteById(id);
    }

    public VoucherResponses findByType(VoucherType type) {
        return VoucherResponses.of(voucherRepository.findByType(type));
    }
}
