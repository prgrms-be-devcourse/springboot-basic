package org.prgrms.kdtspringdemo.domain.voucher;

import org.prgrms.kdtspringdemo.domain.voucher.model.CreateVoucherRequest;
import org.prgrms.kdtspringdemo.domain.voucher.model.Voucher;
import org.prgrms.kdtspringdemo.domain.voucher.model.VoucherType;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/vouchers")
public class VoucherController {
    private final VoucherService voucherService;

    public VoucherController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("")
    public String VouchersPage(Model model) {
        var vouchers = voucherService.getAllVoucherList();
        model.addAttribute("vouchers", vouchers);
        return "vouchers";
    }

    @GetMapping("/{voucherId}")
    public String VoucherDetailPage(@PathVariable("voucherId") UUID voucherId, Model model) {
        var voucher = voucherService.getVoucher(voucherId);
        model.addAttribute("voucher", voucher);
        return "voucher-detail";
    }

    @GetMapping("/new")
    public String createVoucherPage(Model model) {
        var voucherTypes = VoucherType.values();
        model.addAttribute("voucherTypes", voucherTypes);
        return "voucher-new";
    }

    @PostMapping("/new")
    public String CreateVoucher(Model model, CreateVoucherRequest createVoucherRequest) {
        try {
            voucherService.createVoucher(VoucherType.getTypeByName(createVoucherRequest.voucherType()),
                    createVoucherRequest.amountOrPercent());
        } catch (Exception e) {
            e.getStackTrace();
            model.addAttribute("createVoucherFail", true);
            return createVoucherPage(model);
        }

        return "redirect:/vouchers";
    }

    //Rest API
    //crate
    @PostMapping("/api/create")
    @ResponseBody
    public Voucher createVoucher(@RequestBody CreateVoucherRequest createVoucherRequest) {
        return voucherService.createVoucher(VoucherType.getTypeByName(createVoucherRequest.voucherType()),
                createVoucherRequest.amountOrPercent());
    }

    //read
    @GetMapping("/api/find/{voucherId}")
    @ResponseBody
    public Voucher getVoucherById(@PathVariable("voucherId") UUID voucherId) {
        return voucherService.getVoucherById(voucherId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found"));
    }

    @GetMapping("/api/filter")
    @ResponseBody
    public List<Voucher> getVouchers(
            @RequestParam(value = "from", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
            @RequestParam(value = "to", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
            @RequestParam(value = "type", required = false) String type) {
        List<Voucher> vouchers = voucherService.getAllVoucherList();
        List<Voucher> filteredVouchers = vouchers.stream().filter((voucher) -> {
            if (from != null) {
                return voucher.getCreatedAt().isAfter(from);
            } else {
                return true;
            }
        }).filter(voucher -> {
            if (to != null) {
                return voucher.getCreatedAt().isBefore(to);
            } else {
                return true;
            }
        }).filter(voucher -> {
            try {
                VoucherType voucherType = VoucherType.getTypeByName(type);
                return voucher.getVoucherType() == voucherType;
            } catch (Exception e) {
                return false;
            }
        }).collect(Collectors.toList());
        return filteredVouchers;
    }

    //update

    //delete
    @DeleteMapping("/api/{voucherId}")
    public ResponseEntity<Object> deleteVoucher(@PathVariable("voucherId") UUID voucherId) {
        voucherService.deleteVoucherById(voucherId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}
