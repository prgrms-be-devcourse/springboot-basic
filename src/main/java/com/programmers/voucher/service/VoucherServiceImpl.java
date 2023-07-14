package com.programmers.voucher.service;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.domain.VoucherMapper;
import com.programmers.voucher.dto.VoucherRequestDto;
import com.programmers.voucher.dto.VoucherResponseDto;
import com.programmers.voucher.repository.VoucherRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    @Transactional
    public VoucherResponseDto create(VoucherRequestDto requestDto) {
        Voucher voucher = VoucherMapper.convertRequestDtoToDomain(UUID.randomUUID(), requestDto);
        voucherRepository.save(voucher);
        return VoucherMapper.convertDomainToResponseDto(voucher);
    }

    public List<VoucherResponseDto> findVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        return vouchers.stream()
                .map(VoucherMapper::convertDomainToResponseDto)
                .toList();
    }

    //파라미터의 변경을 예측가능 /변경성이 낮음 /일반적이기 때문
    public VoucherResponseDto findVoucherById(UUID voucherID) {
        Voucher voucher = voucherRepository.findById(voucherID);
        return VoucherMapper.convertDomainToResponseDto(voucher);
    }

    public List<VoucherResponseDto> findVouchersByType(String type) {
        List<Voucher> vouchers = voucherRepository.findByType(type);
        return vouchers.stream()
                .map(VoucherMapper::convertDomainToResponseDto)
                .toList();
    }

    public void deleteVoucherById(UUID voucherId) {
        voucherRepository.deleteById(voucherId);
    }

    public Page<Map<String, Object>> findVouchserWithPagination(Pageable pageable) {
        return voucherRepository.findAllByPage(pageable);
    }
}
