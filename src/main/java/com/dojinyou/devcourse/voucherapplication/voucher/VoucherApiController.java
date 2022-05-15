package com.dojinyou.devcourse.voucherapplication.voucher;

import com.dojinyou.devcourse.voucherapplication.voucher.domain.Voucher;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherType;
import com.dojinyou.devcourse.voucherapplication.voucher.dto.VoucherCreateRequest;
import com.dojinyou.devcourse.voucherapplication.voucher.dto.VoucherResponse;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherApiController {
    private final VoucherService voucherService;


    public VoucherApiController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public VoucherResponse create(@RequestBody VoucherCreateRequest createdRequest) {
        Voucher inputVoucher = Voucher.from(createdRequest);
        Voucher respondVoucher = voucherService.create(inputVoucher);
        return VoucherResponse.from(respondVoucher);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public List<VoucherResponse> findAllByCondition(
            @RequestParam("type")
            @Nullable
            VoucherType type,
            @RequestParam("startDate")
            @Nullable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate startDate,
            @RequestParam("endDate")
            @Nullable
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate endDate
   ) {
        if ((startDate == null) ^ (endDate == null)) {
            throw new IllegalArgumentException();
        }
        if (type != null && startDate != null) {
            return toVoucherResponseList(voucherService.findAllByTypeAndCreatedDateBetween(type, startDate, endDate));
        }
        if (type != null) {
            return toVoucherResponseList(voucherService.findAllByType(type));
        }
        if (startDate != null) {
            return toVoucherResponseList(voucherService.findAllByCreatedDateBetween(startDate, endDate));
        }

        return toVoucherResponseList(voucherService.findAll());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public VoucherResponse findById(@PathVariable("id") long id) {
        return VoucherResponse.from(voucherService.findById(id));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable("id") long id) {
        voucherService.deleteById(id);
    }

    private List<VoucherResponse> toVoucherResponseList(List<Voucher> vouchers) {
        return vouchers.stream()
                       .map(VoucherResponse::from)
                       .collect(Collectors.toList());
    }
}
