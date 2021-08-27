package org.prgrms.kdt.command;


import org.prgrms.kdt.model.Voucher;
import org.prgrms.kdt.model.VoucherDTO;
import org.prgrms.kdt.model.VoucherType;
import org.prgrms.kdt.service.VoucherService;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class VoucherCommandOperator implements CommandOperator<Voucher, VoucherDTO> {

    private final VoucherService voucherService;

    public VoucherCommandOperator(VoucherService service) {
        this.voucherService = service;
    }

    @Override
    public Voucher create(VoucherDTO v) {
        var voucherType = VoucherType.lookup(v.name());
        return voucherService.createVoucher(voucherType.createVoucher(v.value()));
    }

    @Override
    public Map<UUID, Voucher> getAll() {
        return voucherService.getAllVouchers();
    }

}
