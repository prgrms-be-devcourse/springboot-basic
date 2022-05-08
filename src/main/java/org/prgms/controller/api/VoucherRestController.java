package org.prgms.controller.api;

import org.prgms.controller.dto.CreateVoucherRequest;
import org.prgms.controller.dto.DeleteVoucherResponse;
import org.prgms.domain.Voucher;
import org.prgms.service.VoucherService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1")
public class VoucherRestController {

    private final VoucherService voucherService;

    public VoucherRestController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping("/voucher")
    public List<Voucher> getAllVouchers() {
        return voucherService.getVouchers();
    }

    @GetMapping("/voucher/type/{voucherType}")
    public List<Voucher> getVoucherByType(@PathVariable String voucherType) {
        return voucherService.getVoucherByType(voucherType);
    }

    @GetMapping("/voucher/created/{begin}/{end}")
    public List<Voucher> getVoucherByCreatedTime(@PathVariable String begin, @PathVariable String end) {
        return voucherService.getVoucherByCreatedTime(begin, end);
    }

    @PostMapping("/voucher")
    public Voucher createVoucher(@RequestBody CreateVoucherRequest voucherRequest) {
        return voucherService.createVoucher(voucherRequest.voucherKind(), voucherRequest.discountAmount());
    }

    @DeleteMapping("/voucher/{voucherId}")
    public DeleteVoucherResponse deleteVoucher(@PathVariable UUID voucherId) {
        int deleteRow = voucherService.deleteVoucher(voucherId);

        return new DeleteVoucherResponse(deleteRow == 1, deleteRow);
    }

    @GetMapping("/voucher/{voucherId}")
    public ResponseEntity<Voucher> getVoucher(@PathVariable UUID voucherId) {
        HttpHeaders headers = new HttpHeaders();

        Optional<Voucher> optionalVoucher = voucherService.getVoucher(voucherId);

        return optionalVoucher
                .map(voucher -> new ResponseEntity<>(voucher, headers, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(headers, HttpStatus.OK));
    }
}
