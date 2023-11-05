package com.programmers.vouchermanagement.voucher.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import jakarta.validation.Valid;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.programmers.vouchermanagement.voucher.domain.VoucherType;
import com.programmers.vouchermanagement.voucher.dto.CreateVoucherRequest;
import com.programmers.vouchermanagement.voucher.dto.SearchCreatedAtRequest;
import com.programmers.vouchermanagement.voucher.dto.VoucherResponse;
import com.programmers.vouchermanagement.voucher.service.VoucherService;

@RestController
@ConditionalOnWebApplication
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
    public List<VoucherResponse> readAllVouchers(String typeName, LocalDate startDate, LocalDate endDate) {
        if (isSearchingType(typeName)) {
            return findByType(typeName);
        }

        if (isSearchingDate(startDate, endDate)) {
            return findByCreatedAt(startDate, endDate);
        }

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

    private boolean isSearchingType(String typeName) {
        return typeName != null;
    }

    private boolean isSearchingDate(LocalDate startDate, LocalDate endDate) {
        return startDate != null && endDate != null;
    }

    private List<VoucherResponse> findByType(String typeName) {
        VoucherType voucherType = VoucherType.findVoucherType(typeName);
        return voucherService.findByType(voucherType);
    }

    private List<VoucherResponse> findByCreatedAt(LocalDate startDate, LocalDate endDate) {
        SearchCreatedAtRequest request = new SearchCreatedAtRequest(startDate, endDate);
        return voucherService.findByCreatedAt(request);
    }
}
