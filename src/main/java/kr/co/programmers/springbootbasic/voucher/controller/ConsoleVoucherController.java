package kr.co.programmers.springbootbasic.voucher.controller;

import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;
import kr.co.programmers.springbootbasic.voucher.dto.VoucherResponse;
import kr.co.programmers.springbootbasic.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;

@Controller
@Profile("console")
public class ConsoleVoucherController {
    private static final Logger logger = LoggerFactory.getLogger(ConsoleVoucherController.class);
    private final VoucherService voucherService;

    public ConsoleVoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public VoucherResponse createVoucher(VoucherType type, long amount) {
        return voucherService.createVoucher(type, amount);
    }

    public List<VoucherResponse> listAllVoucher() {
        return voucherService.listAllVoucher();
    }

    public void deleteById(UUID voucherId) {
        voucherService.deleteById(voucherId);
    }
}
