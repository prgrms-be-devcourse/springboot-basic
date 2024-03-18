package org.prgrms.vouchermanagement.webController;

import org.prgrms.vouchermanagement.dto.VoucherCreateInfo;
import org.prgrms.vouchermanagement.dto.VoucherResponseDto;
import org.prgrms.vouchermanagement.exception.InvalidInputException;
import org.prgrms.vouchermanagement.voucher.domain.Voucher;
import org.prgrms.vouchermanagement.voucher.policy.PolicyStatus;
import org.prgrms.vouchermanagement.voucher.service.VoucherService;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/voucher")
public class VoucherApiController {

    private final VoucherService voucherService;

    public VoucherApiController(VoucherService voucherService) {
        this.voucherService = voucherService;
    }


    @PostMapping
    public VoucherResponseDto createVoucher(@RequestBody VoucherCreateInfo voucherCreateInfo) {
        PolicyStatus policy = validateAndConvertPolicy(voucherCreateInfo.policy().toString());
        long amountOrPercent = validateAndConvertAmountOrPercent(String.valueOf(voucherCreateInfo.amountOrPercent()));

        Voucher voucher = voucherService.createVoucher(policy, amountOrPercent);

        return new VoucherResponseDto(voucher.getVoucherId(), voucher.getDiscountPolicy());
    }

    @GetMapping
    public List<VoucherResponseDto> findVouchers(
            @RequestParam(name = "voucherId", required = false) String voucherId,
            @RequestParam(name = "policy", required = false) String policy
    ) {

        List<VoucherResponseDto> vouchers = new ArrayList<>();

        if (voucherId != null && policy != null) {
            // voucherId와 policy 모두 제공된 경우
            UUID getVoucherId = UUID.fromString(voucherId);
            Voucher findVoucher = voucherService.findVoucher(getVoucherId);
            vouchers.add(new VoucherResponseDto(findVoucher.getVoucherId(), findVoucher.getDiscountPolicy()));
        } else if (voucherId != null) {
            // voucherId만 제공된 경우
            UUID getVoucherId = UUID.fromString(voucherId);
            Voucher findVoucher = voucherService.findVoucher(getVoucherId);
            vouchers.add(new VoucherResponseDto(findVoucher.getVoucherId(), findVoucher.getDiscountPolicy()));
        } else if (policy != null) {
            // policy만 제공된 경우
            PolicyStatus getPolicy = validateAndConvertPolicy(policy);
            List<Voucher> vouchersByPolicy = voucherService.findVouchersByPolicy(getPolicy);
            for (Voucher voucher : vouchersByPolicy) {
                vouchers.add(new VoucherResponseDto(voucher.getVoucherId(), voucher.getDiscountPolicy()));
            }
        } else {
            //검색 조건이 없는 경우 -> 전체 조회
            List<Voucher> getVouchers = voucherService.voucherLists();
            for (Voucher voucher : getVouchers) {
                vouchers.add(new VoucherResponseDto(voucher.getVoucherId(), voucher.getDiscountPolicy()));
            }
        }
        return vouchers;
    }

    @PatchMapping
    public VoucherResponseDto updateVoucher(
            @RequestParam(name = "voucherId") String voucherId,
            @RequestParam(name = "amount") long amount
    ) {

        Voucher voucher = voucherService.updateVoucher(UUID.fromString(voucherId), amount);

        return new VoucherResponseDto(voucher.getVoucherId(), voucher.getDiscountPolicy());
    }

    @DeleteMapping("{voucherId}")
    public VoucherResponseDto deleteVoucher(@PathVariable UUID voucherId) {
        Voucher voucher = voucherService.deleteVoucher(voucherId);

        return new VoucherResponseDto(voucher.getVoucherId(), voucher.getDiscountPolicy());
    }

    private PolicyStatus validateAndConvertPolicy(String inputPolicy) {
        try {
            return PolicyStatus.valueOf(inputPolicy.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new InvalidInputException("잘못된 policy 입력입니다.");
        }
    }

    private long validateAndConvertAmountOrPercent(String inputAmountOrPercent) {
        try {
            return Long.parseLong(inputAmountOrPercent);
        } catch (NumberFormatException e) {
            throw new InvalidInputException("잘못된 amountOrPercent 입력입니다.");
        }
    }

}
