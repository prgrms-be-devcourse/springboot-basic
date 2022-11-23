package com.programmers.commandline.domain.voucher.service;

import com.programmers.commandline.domain.voucher.entity.Voucher;
import com.programmers.commandline.domain.voucher.entity.VoucherType;
import com.programmers.commandline.domain.voucher.repository.VoucherRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }
    public String create(VoucherType voucherType, Long discount) {
        Voucher voucher = voucherType.createVoucher(UUID.randomUUID(), discount);
        return voucherRepository.save(voucher);
    }

    public String findVouchers() {
        StringBuilder sb = new StringBuilder();
        List<Voucher> findAll = voucherRepository.findAll();

        findAll.forEach(voucher ->{
            sb.append(voucher.toString() + "\n");
        });

        return sb.toString();
    }


}
