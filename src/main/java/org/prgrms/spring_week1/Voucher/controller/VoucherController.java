package org.prgrms.spring_week1.Voucher.controller;


import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.prgrms.spring_week1.Voucher.VoucherService;
import org.prgrms.spring_week1.Voucher.model.Voucher;
import org.prgrms.spring_week1.Voucher.model.VoucherType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class VoucherController {

    private VoucherService voucherService;

    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    // voucher 전체조회
    @GetMapping("/vouchers")
    public String voucherList(@RequestParam(required = false) String type, Model model) {
        List<Voucher> voucherList;
        if (type == null) {
            voucherList = voucherService.getAllVoucher();
        } else {
            voucherList = voucherService.findByType(VoucherType.valueOf(type));
        }

        model.addAttribute("vouchers", voucherList);

        return "views/vouchers";
    }

    // voucher 생성페이지
    @GetMapping("/vouchers/new")
    public String newVoucher() {
        return "views/new-voucher";
    }


    // voucher 생성하기
    @PostMapping("/vouchers/new")
    public String createVoucher(CreateVoucherRequest createVoucherRequest) {
        voucherService.createVoucher(Long.parseLong(createVoucherRequest.discount()),
            createVoucherRequest.customerId(),
            VoucherType.valueOf(createVoucherRequest.voucherType()));
        return "redirect:/vouchers";
    }

    //voucher detail보기
    @GetMapping("/vouchers/{voucherId}")
    public String detailVoucher(@PathVariable("voucherId") UUID voucherId, Model model) {
        Optional<Voucher> voucher = voucherService.findById(voucherId);
        model.addAttribute("voucher", voucher.get());
        return "views/voucher-detail";
    }

    //voucher 삭제하기
    @PostMapping("/vouchers/{voucherId}")
    public String deleteVoucher(@PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteByID(voucherId);
        return "redirect:/vouchers";
    }


}
