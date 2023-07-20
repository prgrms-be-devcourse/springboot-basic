package com.dev.voucherproject.controller.web;


import com.dev.voucherproject.controller.web.request.VoucherCreateRequest;
import com.dev.voucherproject.controller.web.response.Response;
import com.dev.voucherproject.model.service.VoucherService;
import com.dev.voucherproject.model.voucher.VoucherDto;
import com.dev.voucherproject.model.voucher.VoucherPolicy;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/v1/vouchers", produces = MediaType.APPLICATION_ATOM_XML_VALUE)
public class VoucherXmlApiController {
    private final VoucherService voucherService;

    public VoucherXmlApiController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_ATOM_XML_VALUE)
    public ResponseEntity<Response<String>> create(@RequestBody VoucherCreateRequest voucherCreateRequest) {
        String id = voucherService.insert(voucherCreateRequest);

        return ResponseEntity.created(URI.create("/api/v1/vouchers/"+id))
                .body(Response.success(id));
    }

    @GetMapping
    public ResponseEntity<Response<List<VoucherDto>>> vouchers(@RequestParam("voucherPolicy") Optional<VoucherPolicy> voucherPolicy) {
        List<VoucherDto> voucherDtos = voucherService.findAllVouchersAppliedQueryString(voucherPolicy);

        return ResponseEntity.ok()
                .body(Response.success(voucherDtos));
    }

    @GetMapping("/date")
    public ResponseEntity<Response<List<VoucherDto>>> betweenDatesCreatedVouchers(
            @RequestParam("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate startDate,
            @RequestParam("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate endDate
    ) {
        List<VoucherDto> voucherDtos = voucherService.findAllBetweenDates(startDate, endDate);

        return ResponseEntity.ok()
                .body(Response.success(voucherDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<VoucherDto>> voucher(@PathVariable String id) {
        VoucherDto voucherDtos = voucherService.findById(id);

        return ResponseEntity.ok()
                .body(Response.success(voucherDtos));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<String>> delete(@PathVariable String id) {
        voucherService.deleteById(id);

        return ResponseEntity.ok()
                .body(Response.success(id));
    }
}
