package org.voucherProject.voucherProject.voucher.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.voucherProject.voucherProject.customer.entity.Customer;
import org.voucherProject.voucherProject.voucher.entity.Voucher;
import org.voucherProject.voucherProject.voucher.entity.VoucherDto;
import org.voucherProject.voucherProject.customer.service.CustomerService;
import org.voucherProject.voucherProject.voucher.service.VoucherService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class SimpleVoucherController implements VoucherController {

    private final VoucherService voucherService;
    private final CustomerService customerService;

    @Override
    public Voucher createVoucher(VoucherDto voucherDto){
        long amount = voucherDto.getAmount();
        Voucher voucher = voucherDto.getVoucherType().createVoucher(amount);

        //customer에 voucher 추가
        Customer customer = customerService.findById(voucherDto.getCustomerId());
        customer.getVouchers().add(voucher.getVoucherId());
        customerService.save(customer);

        return voucherService.save(voucher);
    }

    @Override
    public List<VoucherDto> findAll() {
        List<Voucher> vouchers = voucherService.findAll();
        return vouchers.stream()
                .map(v -> VoucherDto.builder()
                        .voucherType(v.getVoucherType())
                        .amount(v.getHowMuch())
                        .voucherStatus(v.getVoucherStatus())
                        .build())
                .toList();
    }

    @Override
    public void useVoucher(VoucherDto voucherDto) {
        Voucher voucher = voucherService.getVoucher(voucherDto.getVoucherId());
        voucher.useVoucher();
        voucherService.updateVoucher(voucher);
    }

    @Override
    public VoucherDto findById(VoucherDto voucherDto) {
        Voucher voucher = voucherService.getVoucher(voucherDto.getVoucherId());
        return VoucherDto.builder()
                .voucherType(voucher.getVoucherType())
                .voucherStatus(voucher.getVoucherStatus())
                .amount(voucher.getHowMuch())
                .build();
    }
}
