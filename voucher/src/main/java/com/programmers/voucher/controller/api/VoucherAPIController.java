package com.programmers.voucher.controller.api;

import com.programmers.voucher.entity.voucher.DiscountType;
import com.programmers.voucher.entity.voucher.Voucher;
import com.programmers.voucher.entity.voucher.dto.VoucherCreateRequest;
import com.programmers.voucher.entity.voucher.dto.VoucherListRequest;
import com.programmers.voucher.service.voucher.VoucherService;
import com.programmers.voucher.util.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/api/voucher")/*, produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE})*/
public class VoucherAPIController {

    private final VoucherService basicVoucherService;

    public VoucherAPIController(VoucherService basicVoucherService) {
        this.basicVoucherService = basicVoucherService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Voucher>>> listVouchers(@RequestBody(required = false) VoucherListRequest request) {
        if(request == null) request = new VoucherListRequest();
        LocalDate fromDate;
        LocalDate toDate;
        try {
            fromDate = LocalDate.parse(request.getFrom());
            toDate = LocalDate.parse(request.getTo());
        } catch (DateTimeParseException ex) {
            fromDate = LocalDate.of(1970, 1, 1);
            toDate = LocalDate.now();
        }
        List<Voucher> vouchers = basicVoucherService.listAll(fromDate, toDate, Voucher.SearchCriteria.of(request.getCriteria()), request.getKeyword());
        return ResponseEntity.ok(ApiResponse.of(vouchers));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Voucher>> getVoucher(
            @PathVariable("id") long id) {
        Optional<Voucher> byId = basicVoucherService.findById(id);
        return ResponseEntity.ok(byId.map(ApiResponse::of).orElseGet(() -> ApiResponse.failed("No voucher found.")));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Voucher>> createVoucher(
            @RequestBody VoucherCreateRequest request) {
        if(request.getName().isBlank()) {
            ApiResponse<Voucher> response = ApiResponse.failed("Voucher name cannot be empty.");
            return ResponseEntity.badRequest().body(response);
        }

        Voucher voucher = basicVoucherService.create(request.getName(), DiscountType.of(request.getType()), request.getAmount(), request.getOwner());
        if(voucher.getId() < 0) {
            ApiResponse<Voucher> response = ApiResponse.failed("Failed to create voucher. Check your input or if customer is valid.");
            return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.created(URI.create("/api/voucher/" + voucher.getId())).body(ApiResponse.of(voucher));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteVoucher(@PathVariable("id") long id) {
        basicVoucherService.delete(id);
    }
}
