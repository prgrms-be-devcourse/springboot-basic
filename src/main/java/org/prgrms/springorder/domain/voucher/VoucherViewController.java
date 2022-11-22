package org.prgrms.springorder.domain.voucher;

import java.util.List;
import java.util.UUID;
import org.prgrms.springorder.domain.voucher.api.CustomerWithVoucher;
import org.prgrms.springorder.domain.voucher.api.request.DeleteVoucherRequest;
import org.prgrms.springorder.domain.voucher.api.request.VoucherCreateRequest;
import org.prgrms.springorder.domain.voucher.model.Voucher;
import org.prgrms.springorder.domain.voucher.model.VoucherType;
import org.prgrms.springorder.domain.voucher.service.VoucherService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class VoucherViewController {

    private static final Logger logger = LoggerFactory.getLogger(VoucherViewController.class);

    private final VoucherService voucherService;

    public VoucherViewController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/vouchers")
    public String index(Model model) {
        List<Voucher> vouchers = voucherService.findAll();

        model.addAttribute("vouchers", vouchers);

        return "/vouchers";
    }

    @GetMapping("/vouchers/{voucherId}")
    public String getVoucherPage(@PathVariable UUID voucherId, Model model) {
        CustomerWithVoucher customerWithVoucher = voucherService.findVoucherWithCustomerByVoucherId(
            voucherId);

        model.addAttribute("customerWithVoucher", customerWithVoucher);
        return "/voucher-detail";
    }

    @PostMapping("/vouchers/delete")
    public String deleteVoucher(DeleteVoucherRequest voucherDeleteRequest) {
        System.out.println(voucherDeleteRequest.toString());

        voucherService.deleteVoucherByCustomerId(voucherDeleteRequest.getVoucherId(), voucherDeleteRequest.getCustomerId());

        return "redirect:/vouchers";
    }

    @GetMapping("/new-voucher")
    public String createVoucherPage(Model model) {

        model.addAttribute("voucherTypes", VoucherType.values());

        return "/new-voucher";
    }

    @PostMapping("/vouchers/create")
    public String createVoucherRequest(VoucherCreateRequest request) {

        voucherService.createVoucher(request);

        return "redirect:/vouchers";
    }

    @GetMapping("/api/v1/vouchers")
    public @ResponseBody
    ResponseEntity<?> getVoucher() {
        List<Voucher> vouchers = voucherService.findAll();
        return ResponseEntity.ok(vouchers);
    }

}
