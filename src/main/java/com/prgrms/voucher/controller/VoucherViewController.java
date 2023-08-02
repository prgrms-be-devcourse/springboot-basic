package com.prgrms.voucher.controller;

import com.prgrms.common.util.Generator;
import com.prgrms.voucher.controller.dto.VoucherListRequest;
import com.prgrms.voucher.controller.dto.VoucherCreateRequest;
import com.prgrms.voucher.controller.mapper.VoucherControllerConverter;
import com.prgrms.voucher.model.VoucherType;
import com.prgrms.voucher.service.dto.VoucherServiceCreateRequest;
import com.prgrms.voucher.service.dto.VoucherServiceListRequest;
import com.prgrms.voucher.service.dto.VoucherServiceResponse;
import java.time.LocalDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import com.prgrms.voucher.service.VoucherService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/view/vouchers")

public class VoucherViewController {

    private static final String REDIRECT_URL = "redirect:/view/vouchers";

    private final VoucherService voucherService;
    private final Generator generator;
    private final VoucherControllerConverter voucherControllerConverter;

    public VoucherViewController(VoucherService voucherService, Generator generator,
            VoucherControllerConverter voucherControllerConverter) {
        this.voucherService = voucherService;
        this.generator = generator;
        this.voucherControllerConverter = voucherControllerConverter;
    }

    @GetMapping
    public String getVouchers(
            @ModelAttribute VoucherListRequest voucherRequest
            , Model model) {

        VoucherServiceListRequest voucherServiceListRequest = voucherControllerConverter.ofVoucherServiceListRequest(
                voucherRequest);

        model.addAttribute("vouchers",
                voucherService.getAllVoucherList(voucherServiceListRequest));

        return "views/vouchers";
    }

    @GetMapping("/delete/{voucherId}")
    public String deleteVoucher(@PathVariable("voucherId") String voucherId) {
        voucherService.deleteByVoucherId(voucherId);

        return REDIRECT_URL;
    }

    @GetMapping("/detail/{voucherId}")
    public String detailVoucher(@PathVariable("voucherId") String voucherId, Model model) {
        model.addAttribute("voucher", voucherService.detailVoucher(voucherId));

        return "views/detail";
    }

    @GetMapping("/new")
    public String viewFormCreateVoucher(Model model) {
        VoucherType[] voucherTypes = VoucherType.values();
        model.addAttribute("voucherTypes", voucherTypes);

        return "views/create";
    }

    @PostMapping("/new")
    public ResponseEntity<VoucherServiceResponse> createVoucher(
            @ModelAttribute VoucherCreateRequest voucherCreateRequest) {
        String id = generator.makeKey();
        LocalDateTime createdAt = generator.makeDate();

        VoucherServiceCreateRequest voucherServiceCreateRequest = voucherControllerConverter.ofVoucherServiceCreateRequest(
                voucherCreateRequest);

        return new ResponseEntity<>(
                voucherService.createVoucher(id,
                        voucherServiceCreateRequest, createdAt),
                HttpStatus.CREATED);
    }

}
