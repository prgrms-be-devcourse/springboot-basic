package com.programmers.commandLine.domain.voucher.service;

import com.programmers.commandLine.domain.voucher.entity.Voucher;
import com.programmers.commandLine.domain.voucher.entity.VoucherMenu;
import com.programmers.commandLine.domain.voucher.repository.VoucherRepository;
import com.programmers.commandLine.global.factory.LoggerFactory;
import com.programmers.commandLine.global.factory.VoucherFactory;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
/**
 *
 *  VoucherService의 설명을 여기에 작성한다.
 *
 *
 *  @author 장주영
 *  @version 1.0.0
 *  작성일 2022/11/08
 *
**/
@Service
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final VoucherFactory voucherFactory;

    VoucherService(VoucherRepository voucherRepository, VoucherFactory voucherFactory) {
        this.voucherRepository = voucherRepository;
        this.voucherFactory = voucherFactory;
    }

    public UUID create(VoucherMenu voucherMenu, String discount) {
        LoggerFactory.getLogger().info("VoucherService create 실행");

        Voucher voucher = voucherFactory.createVoucher(voucherMenu, discount);
        voucherRepository.save(voucher);
        return voucher.getVoucherId();
    }

    public String list() {
        StringBuilder sb = new StringBuilder();
        Map<String, Voucher> findAll = voucherRepository.findAll();
        findAll.forEach((uuid, voucher) ->{
            sb.append(
                    "ID: " + uuid + " Type: " + voucher.getType() + " discount: " + voucher.getDiscount()
            );
            sb.append("\n");
        });
        return sb.toString();
    }
}
