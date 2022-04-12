package org.voucherProject.voucherProject.controller.voucher;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.voucherProject.voucherProject.entity.voucher.Voucher;
import org.voucherProject.voucherProject.service.voucher.VoucherService;

import java.util.List;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class VoucherControllerImpl implements VoucherController {

    private final VoucherService voucherService;

    @Override
    public Voucher createVoucher(Voucher voucher){
        return voucherService.save(voucher);
    }

    @Override
    public List<Voucher> findAll(){
        return voucherService.findAll();
    }

    @Override
    public Voucher findById(UUID voucherId){
        return voucherService.getVoucher(voucherId);
    }
}
