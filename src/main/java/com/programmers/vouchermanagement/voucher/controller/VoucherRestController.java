package com.programmers.vouchermanagement.voucher.controller;

import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.SearchCreatedAtRequest;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponse;
import com.programmers.vouchermanagement.voucher.service.VoucherService;

@RestController
@RequestMapping(value = "/api/v1/vouchers", produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})
public class VoucherRestController {
    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public VoucherResponse create(@Valid CreateVoucherRequest createVoucherRequest) {
        return voucherService.create(createVoucherRequest);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<VoucherResponse> readAllVouchers() {
        return voucherService.readAllVouchers();
    }

    @GetMapping("/{voucherId}")
    @ResponseStatus(HttpStatus.OK)
    public VoucherResponse findById(@PathVariable UUID voucherId) {
        return voucherService.findById(voucherId);
    }

    @DeleteMapping("/{voucherId}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteById(@PathVariable UUID voucherId) {
        voucherService.deleteById(voucherId);
    }

    @GetMapping("/type/{typeName}")
    @ResponseStatus(HttpStatus.OK)
    public List<VoucherResponse> findByType(@PathVariable String typeName) {
        VoucherType voucherType = VoucherType.findVoucherTypeByName(typeName);
        return voucherService.findByType(voucherType);
    }

    @GetMapping("/creation-date")
    @ResponseStatus(HttpStatus.OK)
    public List<VoucherResponse> findByCreatedAt(@Valid SearchCreatedAtRequest request) {
        return voucherService.findByCreatedAt(request);
    }
}
