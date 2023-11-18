package org.prgms.springbootbasic.controller.voucher;

import org.prgms.springbootbasic.controller.voucher.dto.VoucherRequestDto;
import org.prgms.springbootbasic.controller.voucher.dto.VoucherResponseDto;
import org.prgms.springbootbasic.domain.VoucherType;
import org.prgms.springbootbasic.exception.EntityNotFoundException;
import org.prgms.springbootbasic.service.VoucherService;
import org.prgms.springbootbasic.service.dto.VoucherFilterDto;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/vouchers")
public class VoucherApiController {
    private final VoucherService voucherService;

    public VoucherApiController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }

    @GetMapping
    public ResponseEntity<List<VoucherResponseDto>> showVouchersFiltered(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDay,
                                                                         @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDay,
                                                                         @RequestParam String voucherPolicy) {
        VoucherFilterDto voucherFilterDto = new VoucherFilterDto(startDay, endDay, VoucherType.getTypeFromName(voucherPolicy));

        // 컨트롤러에서 도메인을 바로 바라보는 것은 도메인 로직까지 사용이 가능해서 컨트롤러 역할이 비대해짐.
        // 서비스와 컨트롤러 사이에도 DTO를 둬서 서비스에서만 도메인 처리를 하는 것이 좋음. -> VoucherFilterDto
        // 컨트롤러가 도메인 Voucher를 알고 있다면 Voucher 관련 메서드를 사용할 수 있다는 것이니까. -> Controller와 Service 사이에도 도입한다.
        // 컨트롤러에서 도메인을 볼 수 없도록 한다. 도메인의 수정은 치명적일 수 있고 여기저기서 수정될 수 있다면 유지보수가 힘들 수 있어 보인다.
        // VoucherType을 보는 건 괜찮을까?
        List<VoucherResponseDto> filteredResponseDto = voucherService.findByPolicyBetweenLocalDateTime(voucherFilterDto);

        return ResponseEntity.ok(filteredResponseDto);
    }

    @GetMapping("/{voucherId}")
    public ResponseEntity<VoucherResponseDto> showDetails(@PathVariable String voucherId) {
        VoucherResponseDto voucherResponseDto = voucherService.findById(UUID.fromString(voucherId))
                .orElseThrow(EntityNotFoundException::new);

        return ResponseEntity.ok(voucherResponseDto);
    }

    @PostMapping
    public ResponseEntity<VoucherResponseDto> createVoucher(@RequestBody VoucherRequestDto requestDto) {
        String voucherPolicy = requestDto.voucherPolicy();
        VoucherType voucherType = VoucherType.getTypeFromName(voucherPolicy);

        VoucherResponseDto voucherResponseDto = voucherService.insert(voucherType, requestDto.discountDegree());

        return ResponseEntity.ok(voucherResponseDto);
    }

    @DeleteMapping("/{voucherId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable String voucherId) {
        UUID voucherUUID = UUID.fromString(voucherId);

        voucherService.deleteById(voucherUUID);
    }
}
