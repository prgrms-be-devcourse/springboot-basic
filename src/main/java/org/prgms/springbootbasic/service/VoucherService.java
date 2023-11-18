package org.prgms.springbootbasic.service;

import lombok.extern.slf4j.Slf4j;
import org.prgms.springbootbasic.service.dto.VoucherFilterDto;
import org.prgms.springbootbasic.service.dto.VoucherResponseDto;
import org.prgms.springbootbasic.domain.VoucherType;
import org.prgms.springbootbasic.domain.voucher.Voucher;
import org.prgms.springbootbasic.domain.voucher.VoucherPolicy;
import org.prgms.springbootbasic.exception.EntityNotFoundException;
import org.prgms.springbootbasic.repository.voucher.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherType convertToType(int voucherSeq) {
        return VoucherType.getTypeFromSeq(voucherSeq);
    }

    public VoucherType convertToType(String policyName) {
        return VoucherType.getTypeFromName(policyName);
    }

    public VoucherResponseDto insert(VoucherType voucherType, long discountDegree) {
        VoucherPolicy voucherPolicy = voucherType.create();
        Voucher voucher = new Voucher(UUID.randomUUID(), discountDegree, voucherPolicy);
        Voucher upsertedVoucher = voucherRepository.upsert(voucher);

        return VoucherResponseDto.convertVoucherToVoucherResponseDto(upsertedVoucher);
    }

    public VoucherResponseDto update(UUID voucherId, VoucherType voucherType, long discountDegree) {
        findById(voucherId).orElseThrow(EntityNotFoundException::new);

        Voucher updateVoucher = new Voucher(voucherId, discountDegree, voucherType.create());
        Voucher upsertedVoucher = voucherRepository.upsert(updateVoucher);

        return VoucherResponseDto.convertVoucherToVoucherResponseDto(upsertedVoucher);
    }

    public Optional<VoucherResponseDto> findById(UUID voucherId){
        Optional<Voucher> foundVoucher = voucherRepository.findById(voucherId);
        return foundVoucher.map(VoucherResponseDto::convertVoucherToVoucherResponseDto);
    }

    public List<VoucherResponseDto> findByPolicyBetweenLocalDateTime(VoucherFilterDto voucherFilterDto) {
        List<Voucher> filteredVouchers = voucherRepository.findByPolicyBetweenLocalDateTime(voucherFilterDto.voucherType().getDisplayName(),
                voucherFilterDto.startDay(),
                voucherFilterDto.endDay());

        return filteredVouchers.stream()
                .map(VoucherResponseDto::convertVoucherToVoucherResponseDto).toList();
    }

    public List<VoucherResponseDto> findAll(){
        List<Voucher> vouchers = voucherRepository.findAll();
        return vouchers.stream()
                .map(VoucherResponseDto::convertVoucherToVoucherResponseDto).toList();
    }

    public void deleteById(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }

    public void deleteAll() {
        voucherRepository.deleteAll();
    }
}
