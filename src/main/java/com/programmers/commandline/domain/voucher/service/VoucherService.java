package com.programmers.commandline.domain.voucher.service;

import com.programmers.commandline.domain.voucher.dto.VoucherInsetRequestDto;
import com.programmers.commandline.domain.voucher.entity.Voucher;
import com.programmers.commandline.domain.voucher.entity.VoucherType;
import com.programmers.commandline.domain.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }

    public String insert(VoucherInsetRequestDto requestDto) {
        VoucherType voucherType = VoucherType.valueOf(requestDto.type());
        Voucher voucher = voucherType.createVoucher(UUID.randomUUID(), requestDto.discount(), LocalDateTime.now().withNano(0));
        return voucherRepository.insert(voucher).getId();
    }

    public String insert(String type, long discount) {
        VoucherType voucherType = VoucherType.valueOf(type);
        Voucher voucher = voucherType.createVoucher(UUID.randomUUID(), discount, LocalDateTime.now().withNano(0));
        return voucherRepository.insert(voucher).getId();
    }

    public List<Voucher> findAll() {
        List<Voucher> vouchers = voucherRepository.findAll();
        vouchers.sort((o1, o2) -> o2.getCreatedAt().compareToIgnoreCase(o1.getCreatedAt()));
        return vouchers;
    }

    public void deleteById(String id) {
        voucherRepository.deleteById(id);
    }
}
