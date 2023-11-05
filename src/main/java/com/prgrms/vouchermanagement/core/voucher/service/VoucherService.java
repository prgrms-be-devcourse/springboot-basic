package com.prgrms.vouchermanagement.core.voucher.service;

import com.prgrms.vouchermanagement.core.voucher.domain.Voucher;
import com.prgrms.vouchermanagement.core.voucher.dto.VoucherDto;
import com.prgrms.vouchermanagement.core.voucher.repository.VoucherRepository;
import com.prgrms.vouchermanagement.core.voucher.utils.mapper.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    @Autowired
    public VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public VoucherDto create(VoucherDto voucherDto) {
        Voucher voucher = voucherRepository.save(Mapper.toVoucher(voucherDto));
        return new VoucherDto(voucher.getId(), voucher.getName(), voucher.getAmount(), voucher.getVoucherType());
    }

    public List<VoucherDto> findAll() {
        List<Voucher> voucherList = voucherRepository.findAll();
        return voucherList.stream()
                .map(it -> new VoucherDto(it.getId(), it.getName(), it.getAmount(), it.getVoucherType()))
                .collect(Collectors.toList());
    }
}
