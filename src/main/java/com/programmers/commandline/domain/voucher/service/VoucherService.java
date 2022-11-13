package com.programmers.commandline.domain.voucher.service;

import com.programmers.commandline.domain.voucher.entity.Voucher;
import com.programmers.commandline.domain.voucher.entity.VoucherType;
import com.programmers.commandline.domain.voucher.repository.VoucherRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;

    VoucherService(VoucherRepository voucherRepository) {
        this.voucherRepository = voucherRepository;
    }
    public UUID create(VoucherType voucherType, Long discount) {
        Voucher voucher = voucherType.createVoucher(UUID.randomUUID(), discount);

        voucherRepository.save(voucher);

        return voucher.getVoucherId();
    }

    public String list() {
        StringBuilder sb = new StringBuilder();

        List<Voucher> findAll = voucherRepository.findAll();

        findAll.forEach(voucher ->{
            sb.append("ID: " + voucher.getVoucherId() +
                    " Type: " + voucher.getType().toString() +
                    " discount: " + voucher.getDiscount()
                    + voucher.getAmountUnit()
            );

            sb.append("\n");
        });

        return sb.toString();
    }


}
