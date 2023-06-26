package com.programmers.voucher.service;

import com.programmers.voucher.domain.Voucher;
import com.programmers.voucher.domain.VoucherMapper;
import com.programmers.voucher.dto.VoucherRequestDto;
import com.programmers.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherServiceImpl implements VoucherService {

    private final VoucherRepository voucherRepository;

    public VoucherServiceImpl(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public Voucher create(VoucherRequestDto requestDto) {
        Voucher voucher = VoucherMapper.requestDtoToDomain(requestDto);
        return voucherRepository.save(voucher);
    }

    public List<Voucher> findVouchers() {
        return voucherRepository.findAll();
    }

    public Voucher findVoucher(UUID voucherID) {
        return voucherRepository.findById(voucherID);
    }
}
