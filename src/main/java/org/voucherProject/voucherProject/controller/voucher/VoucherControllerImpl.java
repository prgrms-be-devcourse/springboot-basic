package org.voucherProject.voucherProject.controller.voucher;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.voucherProject.voucherProject.entity.voucher.Voucher;
import org.voucherProject.voucherProject.entity.voucher.VoucherDto;
import org.voucherProject.voucherProject.entity.voucher.VoucherType;
import org.voucherProject.voucherProject.service.voucher.VoucherService;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class VoucherControllerImpl implements VoucherController {

    private final VoucherService voucherService;

    @Override
    public Voucher createVoucher(VoucherDto voucherDto){
        VoucherType voucherType = voucherDto.getVoucherType();
        long amount = voucherDto.getAmount();
        return voucherService.save(voucherType.createVoucher(amount));
    }

    @Override
    public List<Voucher> findAll(){
        return voucherService.findAll();
    }

    @Override
    public Voucher findById(UUID voucherId){
        return voucherService.getVoucher(voucherId);
    }

    @Override
    public void printAll() {
        voucherService.findAll().forEach(voucher -> System.out.println("voucher = " + voucher));
    }
}
