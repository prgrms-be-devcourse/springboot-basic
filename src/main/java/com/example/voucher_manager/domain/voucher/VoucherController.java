package com.example.voucher_manager.domain.voucher;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
public class VoucherController {

    private final VoucherService voucherService;

    private static final Logger logger = LoggerFactory.getLogger(VoucherController.class);

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    // ===== API =====
    @GetMapping("/api/v1/vouchers")
    @ResponseBody
    public ResponseEntity<List<VoucherDto>> findVouchers(@RequestParam Map<String, String> params) {
        if (params.isEmpty()) {
            List<VoucherDto> vouchers = voucherService.findAll().stream()
                    .map(VoucherDto::from)
                    .toList();
            return ResponseEntity.ok(vouchers);
        }

        var vouchers = findByQueryParams(params).stream()
                .map(VoucherDto::from)
                .toList();
        return ResponseEntity.ok(vouchers);
    }

    private List<Voucher> findByQueryParams(Map<String, String> params) {
        if (params.containsKey("type")){
            return voucherService.findSameTypeVoucher(VoucherType.of(params.get("type")));
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS");
        System.out.println("params.get(\"start\") = " + params.get("start"));
        System.out.println("params.get(\"end\") = " + params.get("end"));
        return voucherService.findVouchersByPeriods(
                LocalDateTime.parse(params.get("start"), formatter),
                LocalDateTime.parse(params.get("end"), formatter));
    }

    @GetMapping("/api/v1/vouchers/{voucherId}")
    @ResponseBody
    public ResponseEntity<VoucherDto> findVoucher(@PathVariable("voucherId") UUID voucherId) {
        var voucher = voucherService.findVoucher(voucherId);
        if (voucher.isPresent()) {
            var voucherDto = VoucherDto.from(voucher.get());
            return ResponseEntity.ok(voucherDto);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/api/v1/vouchers")
    @ResponseBody
    public ResponseEntity<VoucherDto> createVoucher(@RequestBody CreateVoucherRequest createVoucherRequest) {
        var voucher = voucherService.createVoucher(
                VoucherType.of(createVoucherRequest.voucherType()),
                createVoucherRequest.discountInfo());

        return ResponseEntity.ok(VoucherDto.from(voucher));
    }

    @DeleteMapping("/api/v1/vouchers/{voucherId}")
    @ResponseBody
    public ResponseEntity<String> deleteVoucher(@PathVariable("voucherId") UUID voucherId) {
        var response = voucherService.deleteVoucher(voucherId);
        return response ? ResponseEntity.ok("Success") : ResponseEntity.internalServerError().build();
    }

    // ===== VIEWS =====
    @GetMapping("/vouchers")
    public String viewVouchersPage(Model model) {
        List<VoucherDto> convertObject = voucherService.findAll().stream()
                .map(VoucherDto::from)
                .toList();
        model.addAttribute("vouchers", convertObject);
        return "views/voucher/vouchers";
    }

    @GetMapping("/vouchers/{voucherId}")
    public String findVoucher(@PathVariable UUID voucherId, Model model) {
        var findVoucher = voucherService.findVoucher(voucherId);
        if (findVoucher.isPresent()) {
            model.addAttribute("voucher", VoucherDto.from(findVoucher.get()));
            return "views/voucher/voucher-details";
        }
        return "views/error/404";
    }

    @PostMapping("/vouchers/{voucherId}")
    public String modifyVoucher(VoucherDto voucherDto) {
        logger.info("Got voucher data modify request {}", voucherDto);
        voucherService.updateVoucher(VoucherDto.toEntity(voucherDto));
        return "redirect:/vouchers";
    }

    @GetMapping("/vouchers/new")
    public String viewCreateVoucherPage() {
        return "views/voucher/voucher-new";
    }

    @PostMapping("/vouchers/new")
    public String createVoucherPage(CreateVoucherRequest createVoucherRequest) {
        logger.info("Got create voucher request {}", createVoucherRequest);
        voucherService.createVoucher(
                VoucherType.of(createVoucherRequest.voucherType()),
                createVoucherRequest.discountInfo());
        return "redirect:/vouchers";
    }

    @GetMapping("/vouchers/remove/{voucherId}")
    public String removeVoucher(@PathVariable UUID voucherId) {
        logger.info("Got voucher remove request {}", voucherId);
        if (voucherService.deleteVoucher(voucherId)) {
            return "redirect:/vouchers";
        }
        return "views/error/504";
    }
}
