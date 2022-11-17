package org.prgrms.springorder.domain.voucher.api;

import java.util.Arrays;
import java.util.List;
import org.prgrms.springorder.console.io.ListResponse;
import org.prgrms.springorder.console.io.Response;
import org.prgrms.springorder.console.io.StringResponse;
import org.prgrms.springorder.domain.voucher.api.request.VoucherCreateRequest;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class VoucherController {

    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    public Response createVoucher(VoucherCreateRequest voucherCreateRequest) {
        Voucher voucher = voucherService.createVoucher(voucherCreateRequest);

        return new StringResponse(voucher);
    }

    public Response findAllVoucher() {
        List<Voucher> vouchers = voucherService.findAll();
        return new ListResponse(vouchers);
    }

    public Response findCustomerWithVoucher(VoucherIdRequest voucherIdRequest) {
        CustomerWithVoucher customerWithVoucher = voucherService.findVoucherWithCustomerByVoucherId(
            voucherIdRequest.getVoucherId());

        return new StringResponse(customerWithVoucher);
    }

    @GetMapping("/index")
    public String index(Model model) {
        List<Voucher> vouchers = voucherService.findAll();

        model.addAttribute("vouchers", vouchers);

        return "/index";
    }


    @GetMapping("/api/v1/vouchers")
    public @ResponseBody
    ResponseEntity<?> getVoucher() {
        List<Voucher> vouchers = voucherService.findAll();
        return ResponseEntity.ok(vouchers);
    }
}
