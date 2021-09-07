package com.programmers.voucher.controller.api;

import com.programmers.voucher.entity.voucher.DiscountPolicy;
import com.programmers.voucher.entity.voucher.Voucher;
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
        return ResponseEntity.ok(ApiResponse.of(vouchers));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Voucher>> getVoucher(
            @PathVariable("id") long id) {
        Optional<Voucher> byId = basicVoucherService.findById(id);
        return ResponseEntity.ok(byId.map(ApiResponse::of).orElseGet(() -> ApiResponse.failed("No voucher found.")));
    }

    static class CreateVoucher {
        String name;
        String type;
        int amount;
        long owner;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getAmount() {
            return amount;
        }

        public void setAmount(int amount) {
            this.amount = amount;
        }

        public long getOwner() {
            return owner;
        }

        public void setOwner(long owner) {
            this.owner = owner;
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Voucher>> createVoucher(
            @RequestBody CreateVoucher request) {
        if(request.name.isBlank()) {
            ApiResponse<Voucher> response = ApiResponse.failed("Voucher name cannot be empty.");
            return ResponseEntity.badRequest().body(response);
        }

        Voucher voucher = basicVoucherService.create(request.name, DiscountPolicy.Type.of(request.type), request.amount, request.owner);
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
