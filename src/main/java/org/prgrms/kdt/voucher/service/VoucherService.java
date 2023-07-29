package org.prgrms.kdt.voucher.service;

import org.prgrms.kdt.global.exception.EntityNotFoundException;
import org.prgrms.kdt.voucher.domain.Voucher;
import org.prgrms.kdt.voucher.dao.VoucherRepository;
import org.prgrms.kdt.voucher.domain.VoucherType;
import org.prgrms.kdt.voucher.service.dto.CreateVoucherRequest;
import org.prgrms.kdt.voucher.service.dto.VoucherDetailResponse;
import org.prgrms.kdt.voucher.service.dto.VoucherResponse;
import org.prgrms.kdt.voucher.service.dto.VoucherResponses;
import org.prgrms.kdt.voucher.service.mapper.ServiceVoucherMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class VoucherService {
    private final VoucherRepository voucherRepository;
    private final ServiceVoucherMapper mapper;

    public VoucherService(VoucherRepository voucherRepository, ServiceVoucherMapper mapper) {
        this.voucherRepository = voucherRepository;
        this.mapper = mapper;
    }

    public VoucherResponse createVoucher(CreateVoucherRequest request) {
        Voucher voucher = voucherRepository.insert(mapper.convertVoucher(request));
        return new VoucherResponse(voucher);
    }

    public VoucherResponses findAll() {
        return VoucherResponses.of(voucherRepository.findAll());
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
