package kr.co.programmers.springbootbasic.voucher.controller;

import kr.co.programmers.springbootbasic.customer.dto.CustomerResponse;
import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;
import kr.co.programmers.springbootbasic.voucher.dto.VoucherCreateRequest;
import kr.co.programmers.springbootbasic.voucher.dto.VoucherResponse;
import kr.co.programmers.springbootbasic.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
public class VoucherController {
    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
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
