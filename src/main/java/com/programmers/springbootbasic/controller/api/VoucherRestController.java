package com.programmers.springbootbasic.controller.api;

import com.programmers.springbootbasic.controller.dto.Benefit;
import com.programmers.springbootbasic.controller.api.response.Response;
import com.programmers.springbootbasic.domain.Customer;
import com.programmers.springbootbasic.domain.Voucher;
import com.programmers.springbootbasic.service.CustomerVoucherLogService;
import com.programmers.springbootbasic.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.programmers.springbootbasic.consolestarter.VoucherManager.validateVoucherId;
import static com.programmers.springbootbasic.controller.api.response.ResponseMessage.getCommonResponseEntity;
import static com.programmers.springbootbasic.controller.api.response.ResponseMessage.getErrorResponseEntity;
import static com.programmers.springbootbasic.validation.VoucherValidator.NON_EXISTING_VOUCHER_ID_EXCEPTION_MESSAGE;

@RestController
@RequestMapping(path = "/api/v1")
public class VoucherRestController {

    private final VoucherService voucherService;
    private final CustomerVoucherLogService customerVoucherLogService;

    public VoucherRestController(VoucherService voucherService, CustomerVoucherLogService customerVoucherLogService) {
        this.voucherService = voucherService;
        this.customerVoucherLogService = customerVoucherLogService;
    }

    @PostMapping("/vouchers")
    public ResponseEntity<? extends Response> createVoucher(@RequestBody Benefit benefit) {
        try {
            Voucher voucher = voucherService.createVoucher(benefit);
            return getCommonResponseEntity(voucher, "새로운 할인권의 정보를 성공적으로 저장하였습니다.");
        } catch (IllegalArgumentException e) {
            return getErrorResponseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @GetMapping("/vouchers/available")
    public ResponseEntity<? extends Response> showAvailableVoucherList() {
        List<Voucher> availableVouchers = voucherService.getAvailableVouchers();
        if (availableVouchers.size() > 0)
            return getCommonResponseEntity(availableVouchers, "이용 가능한 할인권 리스트입니다.");
        else
            return getErrorResponseEntity(HttpStatus.NO_CONTENT, "현재 이용 가능한 할인권이 없습니다.");
    }

    @GetMapping("/vouchers")
    public ResponseEntity<? extends Response> showVoucherList() {
        List<Voucher> allVouchers = voucherService.getAllVouchers();
        if (allVouchers.size() > 0)
            return getCommonResponseEntity(allVouchers, "모든 할인권 정보를 담은 리스트입니다.");
        else
            return getErrorResponseEntity(HttpStatus.NO_CONTENT, "현재 저장된 할인권 정보가 없습니다.");
    }

    @GetMapping("/vouchers/{id}")
    public ResponseEntity<? extends Response> showVoucherInfo(@PathVariable("id") String voucherId) {
        try {
            validateVoucherId(voucherId);
            Optional<Voucher> foundVoucher = voucherService.getVoucherById(UUID.fromString(voucherId));

            if (foundVoucher.isEmpty())
                return getErrorResponseEntity(HttpStatus.BAD_REQUEST, NON_EXISTING_VOUCHER_ID_EXCEPTION_MESSAGE);

            Optional<Customer> customerHoldingVoucher = customerVoucherLogService.getCustomerHoldingVoucher(UUID.fromString(voucherId));
            return customerHoldingVoucher
                    .map(customer -> getCommonResponseEntity(Arrays.asList(customer, foundVoucher.get()), "해당 할인권을 보유한 고객과 할인권 정보입니다."))
                    .orElseGet(() -> getCommonResponseEntity(foundVoucher.get(), "해당 할인권은 현재 이용 가능합니다."));
        } catch (IllegalArgumentException e) {
            return getErrorResponseEntity(HttpStatus.BAD_REQUEST, e.getMessage());
        }
    }

    @DeleteMapping("/vouchers/{id}")
    public void deleteVoucher(@PathVariable("id") String voucherId) {
        voucherService.deleteVoucherById(UUID.fromString(voucherId));
    }

}
