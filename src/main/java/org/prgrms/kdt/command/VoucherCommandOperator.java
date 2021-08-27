package org.prgrms.kdt.command;


import java.text.MessageFormat;
import org.prgrms.kdt.model.Voucher;
import org.prgrms.kdt.model.VoucherDTO;
import org.prgrms.kdt.model.VoucherType;
import org.prgrms.kdt.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class VoucherCommandOperator implements CommandOperator<Voucher, VoucherDTO> {

    private static final Logger logger = LoggerFactory.getLogger(VoucherCommandOperator.class);
    private final VoucherService voucherService;

    public VoucherCommandOperator(VoucherService service) {
        this.voucherService = service;
    }

    @Override
    public Voucher create(VoucherDTO v) {
        logger.info(MessageFormat.format("input voucher data: {0}", v.toString()));
        var voucherType = VoucherType.lookup(v.name());
        return voucherService.createVoucher(voucherType.createVoucher(v.value()));
    }

    @Override
    public Map<UUID, Voucher> getAll() {
        return voucherService.getAllVouchers();
    }

}
