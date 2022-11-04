package com.programmers.commandLine.domain.voucher.service;

import com.programmers.commandLine.domain.voucher.entity.Voucher;
import com.programmers.commandLine.domain.voucher.entity.VoucherMenu;
import com.programmers.commandLine.domain.voucher.repository.VoucherRepository;
import com.programmers.commandLine.global.factory.VoucherFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherFactory voucherFactory;

    VoucherService(VoucherRepository voucherRepository, VoucherFactory voucherFactory) {
        this.voucherRepository = voucherRepository;
        this.voucherFactory = voucherFactory;
    }

    public UUID create(VoucherMenu voucherMenu, String discount) {
        Voucher voucher = voucherFactory.createVoucher(voucherMenu, discount);
        voucherRepository.save(voucher);
        return voucher.getVoucherId();
    }

    public String list() {
        int count = 0;
        StringBuilder sb = new StringBuilder();
        Map<UUID, Voucher> findAll = voucherRepository.findAll();
        findAll.forEach((uuid, voucher) ->
                sb.append(
                        count + ". ID: " + uuid + " Type: " + voucher.getType() + " discount: " + voucher.getDiscount()
                ));

        return sb.toString();
    }
}
