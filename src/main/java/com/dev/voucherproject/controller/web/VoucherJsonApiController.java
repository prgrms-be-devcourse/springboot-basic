package com.dev.voucherproject.controller.web;


import com.dev.voucherproject.controller.web.request.VoucherCreateRequest;
import com.dev.voucherproject.controller.web.response.Response;
import com.dev.voucherproject.model.service.VoucherService;
import com.dev.voucherproject.model.voucher.VoucherDto;
import com.dev.voucherproject.model.voucher.VoucherPolicy;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/vouchers", produces = MediaType.APPLICATION_JSON_VALUE)
public class VoucherJsonApiController {
    private final VoucherService voucherService;

    public VoucherJsonApiController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Response<Void> create(@RequestBody VoucherCreateRequest voucherCreateRequest) {
        voucherService.insert(voucherCreateRequest);

        return Response.success(null);
    }

    @GetMapping
    public Response<List<VoucherDto>> vouchers(@RequestParam("policy") Optional<VoucherPolicy> policy) {
        List<VoucherDto> vouchers = policy
                .map(voucherService::findAllVouchersByPolicy)
                .orElse(voucherService.findAllVouchers())
                .stream().toList();

        return Response.success(vouchers);
    }

    @GetMapping("/date")
    public Response<List<VoucherDto>> betweenDatesCreatedVouchers(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
    ) {
        List<VoucherDto> vouchers = voucherService.findAllBetweenDates(startDate, endDate);

        return Response.success(vouchers);
    }

    @GetMapping("/{id}")
    public Response<VoucherDto> voucher(@PathVariable String id) {
        VoucherDto voucher = voucherService.findById(id);

        return Response.success(voucher);
    }

    @DeleteMapping("/{id}")
    public Response<Void> delete(@PathVariable String id) {
        voucherService.deleteById(id);

        return Response.success(null);
    }
}
