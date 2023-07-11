package com.dev.voucherproject.controller.web;


import com.dev.voucherproject.controller.web.request.VoucherCreateRequest;
import com.dev.voucherproject.controller.web.response.Response;
import com.dev.voucherproject.model.voucher.VoucherApplication;
import com.dev.voucherproject.model.voucher.VoucherDto;
import com.dev.voucherproject.model.voucher.VoucherPolicy;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/api/v1/vouchers", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoucherJsonApiController {
    private final VoucherApplication voucherApplication;

    public VoucherJsonApiController(VoucherApplication voucherApplication) {
        this.voucherApplication = voucherApplication;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Void> create(@RequestBody VoucherCreateRequest voucherCreateRequest) {
        voucherApplication.insert(voucherCreateRequest);

        return Response.success(null);
    }

    @GetMapping
    public Response<List<VoucherDto>> vouchers() {
        List<VoucherDto> vouchers = voucherApplication.findAllVouchers();

        return Response.success(vouchers);
    }

    @GetMapping("/searchPolicy/{policy}")
    public Response<List<VoucherDto>> specificPolicyVouchers(@PathVariable("policy") VoucherPolicy voucherPolicy) {
        List<VoucherDto> vouchers = voucherApplication.findAllVouchersByPolicy(voucherPolicy);

        return Response.success(vouchers);
    }

    @GetMapping("/date")
    public Response<List<VoucherDto>> betweenDatesCreatedVouchers(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
    ) {
        List<VoucherDto> vouchers = voucherApplication.findAllBetweenDates(startDate, endDate);

        return Response.success(vouchers);
    }

    @GetMapping("/{id}")
    public Response<VoucherDto> voucher(@PathVariable String id) {
        VoucherDto voucher = voucherApplication.findById(id);

        return Response.success(voucher);
    }

    @DeleteMapping("/{id}")
    public Response<Void> delete(@PathVariable String id) {
        voucherApplication.deleteById(id);

        return Response.success(null);
    }
}
