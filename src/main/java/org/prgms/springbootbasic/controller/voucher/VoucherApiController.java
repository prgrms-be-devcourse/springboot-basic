package org.prgms.springbootbasic.controller.voucher;

import org.prgms.springbootbasic.common.UtilMethod;
import org.prgms.springbootbasic.domain.VoucherType;
import org.prgms.springbootbasic.domain.voucher.Voucher;
import org.prgms.springbootbasic.domain.voucher.VoucherRequestDto;
import org.prgms.springbootbasic.domain.voucher.VoucherResponseDto;
import org.prgms.springbootbasic.exception.EntityNotFoundException;
import org.prgms.springbootbasic.service.VoucherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.prgms.springbootbasic.common.CommonConstant.MAX_LOCAL_DATE_TIME;
import static org.prgms.springbootbasic.common.CommonConstant.MIN_LOCAL_DATE_TIME;
import static org.prgms.springbootbasic.common.UtilMethod.convertVoucherToVoucherResponseDto;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherApiController {
    private final VoucherService voucherService;

    public VoucherApiController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public ResponseEntity<List<VoucherResponseDto>> showVouchersFiltered(@RequestParam(required = false) String startDate,
                                                                         @RequestParam(required = false) String endDate,
                                                                         @RequestParam(required = false) String policy) {
        LocalDateTime startOfDay = (startDate != null) ?
                LocalDate.parse(startDate).atStartOfDay() : MIN_LOCAL_DATE_TIME;
        LocalDateTime endOfDay = (endDate != null) ?
                LocalDate.parse(endDate).atTime(23, 59, 59) : MAX_LOCAL_DATE_TIME;
        String voucherPolicy = (policy != null) ? policy : "%";

        List<Voucher> vouchers = voucherService.findByPolicyBetweenLocalDateTime(voucherPolicy, startOfDay, endOfDay); // 이게 맞나??
        List<VoucherResponseDto> responseDto = vouchers.stream().map(UtilMethod::convertVoucherToVoucherResponseDto).toList();

        return ResponseEntity.ok(responseDto);
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<VoucherResponseDto> showDetails(@PathVariable String voucherId) {
        Voucher voucher = voucherService.findById(UUID.fromString(voucherId))
                .orElseThrow(EntityNotFoundException::new);
        VoucherResponseDto voucherResponseDto = convertVoucherToVoucherResponseDto(voucher);

        return ResponseEntity.ok(voucherResponseDto);
    }

    @PostMapping
    public ResponseEntity<VoucherResponseDto> createVoucher(@RequestBody VoucherRequestDto requestDto) {
        String voucherPolicy = requestDto.voucherPolicy();
        VoucherType voucherType = voucherService.convertToType(voucherPolicy);

        Voucher insertedVoucher = voucherService.insert(voucherType, requestDto.discountDegree());

        return ResponseEntity.ok(convertVoucherToVoucherResponseDto(insertedVoucher));
    }

    @DeleteMapping("/{voucherId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable String voucherId) {
        UUID voucherUUID = UUID.fromString(voucherId);

        voucherService.deleteById(voucherUUID);
    }
}
