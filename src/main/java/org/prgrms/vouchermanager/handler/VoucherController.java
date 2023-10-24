package org.prgrms.vouchermanager.handler;

import lombok.RequiredArgsConstructor;
import org.prgrms.vouchermanager.repository.voucher.JdbcVoucherRepository;
import org.prgrms.vouchermanager.domain.voucher.FixedAmountVoucher;
import org.prgrms.vouchermanager.domain.voucher.PercentDiscountVoucher;
import org.prgrms.vouchermanager.domain.voucher.Voucher;
import org.prgrms.vouchermanager.domain.voucher.VoucherType;
import org.prgrms.vouchermanager.repository.voucher.MemoryVoucherRepository;
import org.prgrms.vouchermanager.repository.voucher.VoucherRepository;
import org.prgrms.vouchermanager.service.VoucherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class VoucherController {

    private final VoucherService voucherService;

//    public VoucherController(VoucherRepository voucherRepository) {
//        this.voucherService = new VoucherService(voucherRepository);
//    }

    public void create(VoucherType voucherType) {
        if(voucherType == VoucherType.FIXED)
            voucherService.createVoucher(new FixedAmountVoucher(UUID.randomUUID(), 10, VoucherType.FIXED));
        else
            voucherService.createVoucher(new PercentDiscountVoucher(UUID.randomUUID(), 10, VoucherType.PERCENT));
    }

    public List<Voucher> findAllVoucher() {
        return voucherService.getAllVoucher();
    }
}
