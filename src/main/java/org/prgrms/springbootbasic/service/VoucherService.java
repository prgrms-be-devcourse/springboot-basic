package org.prgrms.springbootbasic.service;

import lombok.RequiredArgsConstructor;
import org.prgrms.springbootbasic.dto.VoucherInputDto;
import org.prgrms.springbootbasic.entity.Voucher;
import org.prgrms.springbootbasic.exception.VoucherNotFoundException;
import org.prgrms.springbootbasic.mapper.VoucherDtoMapper;
import org.prgrms.springbootbasic.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class VoucherService {
    private final VoucherRepository voucherRepository;

    public Voucher createVoucher(VoucherInputDto voucherInputDto) {
        Voucher voucher = VoucherDtoMapper.VoucherInputDtoToVoucher(voucherInputDto);
        voucherRepository.insert(voucher);
        return voucher;
    }

    public Voucher lookupVoucherById(String voucherId) {
        return voucherRepository.findById(UUID.fromString(voucherId))
                .orElseThrow(VoucherNotFoundException::new);
    }

    public List<Voucher> lookupVoucherList() {
        return voucherRepository.findAll();
    }
}
