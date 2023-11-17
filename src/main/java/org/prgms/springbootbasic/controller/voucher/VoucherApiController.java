package org.prgms.springbootbasic.controller.voucher;

import org.prgms.springbootbasic.domain.VoucherType;
import org.prgms.springbootbasic.domain.voucher.Voucher;
import org.prgms.springbootbasic.controller.voucher.dto.VoucherRequestDto;
import org.prgms.springbootbasic.controller.voucher.dto.VoucherResponseDto;
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
import static org.prgms.springbootbasic.controller.voucher.dto.VoucherResponseDto.convertVoucherToVoucherResponseDto;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherApiController {
    private final VoucherService voucherService;

    public VoucherApiController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }


    // get도 body 지원. 서버 처리, POST는 추가 생성 전송이 아니고.
    @GetMapping
    public ResponseEntity<List<VoucherResponseDto>> showVouchersFiltered(@RequestParam(required = false) String startDate,
                                                                         @RequestParam(required = false) String endDate,
                                                                         @RequestParam(required = false) String policy) {
        LocalDateTime startOfDay = (startDate != null) ?
                LocalDate.parse(startDate).atStartOfDay() : MIN_LOCAL_DATE_TIME;
        LocalDateTime endOfDay = (endDate != null) ?
                LocalDate.parse(endDate).atTime(23, 59, 59) : MAX_LOCAL_DATE_TIME;
        String voucherPolicy = (policy != null) ? policy : "%";
        // 얘를 서비스에서 처리하도록 하는 것이 깔끔함.

        // 컨트롤러에서 도메인을 바로 바라보는 것은 도메인 로직까지 사용이 가능해서 컨트롤러 역할이 비대해짐.
        // 서비스와 컨트롤러 사이에도 DTO를 둬서 서비스에서만 도메인 처리를 하는 것이 좋음.
        // 컨트롤러가 도메인 Voucher를 알고 있다면 Voucher 관련 메서드를 사용할 수 있다는 것이니까.
        List<Voucher> vouchers = voucherService.findByPolicyBetweenLocalDateTime(voucherPolicy, startOfDay, endOfDay); // 이게 맞나??
        // -> 한번에, 동적 쿼리로. 대부분 널 처리로도 위 기본값 넣는 것처럼 해도 됨. 서비스 정책에 따라 다름.
        List<VoucherResponseDto> responseDto = vouchers.stream().map(VoucherResponseDto::convertVoucherToVoucherResponseDto).toList();

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
