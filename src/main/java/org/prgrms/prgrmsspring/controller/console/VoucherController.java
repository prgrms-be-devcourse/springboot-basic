package org.prgrms.prgrmsspring.controller.console;

import org.prgrms.prgrmsspring.domain.VoucherType;
import org.prgrms.prgrmsspring.entity.voucher.Voucher;
import org.prgrms.prgrmsspring.service.VoucherService;
import org.springframework.stereotype.Controller;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
public class VoucherController implements ApplicationController {

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public Voucher create(Voucher voucher) {
        return voucherService.create(voucher);
    }

    public Voucher update(Voucher updateVoucher) {
        return voucherService.update(updateVoucher);
    }

    public void delete(UUID deleteVoucherId) {
        voucherService.delete(deleteVoucherId);
    }

    public List<Voucher> findAll() {
        return voucherService.findAll();
    }

    public Voucher findById(UUID voucherId) {
        return voucherService.findById(voucherId);
    }

    public List<Voucher> findBetweenDate(LocalDateTime begin, LocalDateTime end) {
        return voucherService.findBetweenDate(begin, end);
    }

    public List<Voucher> findByVoucherType(int modeNum) {
        VoucherType voucherType = VoucherType.from(modeNum);
        return voucherService.findByVoucherType(voucherType);
    }
}
