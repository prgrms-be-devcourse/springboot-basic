package org.voucherProject.voucherProject.controller.voucher;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.voucherProject.voucherProject.entity.customer.Customer;
import org.voucherProject.voucherProject.entity.voucher.Voucher;
import org.voucherProject.voucherProject.entity.voucher.VoucherDto;
import org.voucherProject.voucherProject.service.customer.CustomerService;
import org.voucherProject.voucherProject.service.voucher.VoucherService;

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

        Customer customer = customerService.findById(voucherDto.getCustomerId());
        customer.getVouchers().add(voucher.getVoucherId());
        customerService.save(customer);

        return voucherService.save(voucher);
    }

    @Override
    public List<VoucherDto> findAll() {
        List<Voucher> vouchers = voucherService.findAll();
        return vouchers.stream()
                .map(v -> new VoucherDto(v.getVoucherType(), v.getHowMuch(), v.getVoucherStatus()))
                .toList();
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
