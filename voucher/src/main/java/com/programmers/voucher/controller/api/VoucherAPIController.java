package com.programmers.voucher.controller.api;

import com.programmers.voucher.entity.voucher.Voucher;
import com.programmers.voucher.service.voucher.VoucherService;
import com.programmers.voucher.util.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

@RestController
@RequestMapping(value = "/api/voucher")/*, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})*/
public class VoucherAPIController {

    private final VoucherService basicVoucherService;

    public VoucherAPIController(VoucherService basicVoucherService) {
        this.basicVoucherService = basicVoucherService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Voucher>>> listVouchers(
            @RequestParam(name = "from", required = false, defaultValue = "1970-01-01") String from,
            @RequestParam(name = "to", required = false, defaultValue = "2999-12-31") String to,
            @RequestParam(name = "criteria", required = false, defaultValue = "") String criteria,
            @RequestParam(name = "keyword", required = false, defaultValue = "") String keyword) {
        LocalDate fromDate;
        LocalDate toDate;
        try {
            fromDate = LocalDate.parse(from);
            toDate = LocalDate.parse(to);
        } catch (DateTimeParseException ex) {
            fromDate = LocalDate.of(1970, 1, 1);
            toDate = LocalDate.now();
        }
        List<Voucher> vouchers = basicVoucherService.listAll(fromDate, toDate, Voucher.SearchCriteria.of(criteria), keyword);
        return ResponseEntity.ok(new ApiResponse<>(true, vouchers));
    }
}
