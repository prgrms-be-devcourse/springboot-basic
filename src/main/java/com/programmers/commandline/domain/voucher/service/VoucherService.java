package com.programmers.commandline.domain.voucher.service;

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

    public String insert(VoucherType voucherType, long discount) {
        Voucher voucher = voucherType.createVoucher(UUID.randomUUID(), discount, LocalDateTime.now());
        // 따로작업을 하지 않으니 Test 중요도 극히 낮다.
        Voucher voucher1 = voucherRepository.insert(voucher);
        return voucher1.getId();
    }

    public String findAll() {
        StringBuilder sb = new StringBuilder();
        List<Voucher> findAll = voucherRepository.findAll();

        findAll.forEach(voucher -> sb.append(voucher.toString()));

        return sb.toString();
    }


}
