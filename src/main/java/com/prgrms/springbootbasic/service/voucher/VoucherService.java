package com.prgrms.springbootbasic.service.voucher;

import com.prgrms.springbootbasic.domain.voucher.FixedVoucher;
import com.prgrms.springbootbasic.domain.voucher.RateVoucher;
import com.prgrms.springbootbasic.domain.voucher.Voucher;
import com.prgrms.springbootbasic.dto.voucher.request.VoucherCreateRequest;
import com.prgrms.springbootbasic.dto.voucher.request.VoucherUpdateRequest;
import com.prgrms.springbootbasic.dto.voucher.response.VoucherListResponse;
import com.prgrms.springbootbasic.dto.voucher.response.VoucherResponse;
import com.prgrms.springbootbasic.enums.VoucherType;
import com.prgrms.springbootbasic.repository.voucher.VoucherRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@AllArgsConstructor
public class VoucherService {

    private VoucherRepository voucherRepository;

    //생성(create)
    public Voucher createVoucher(VoucherCreateRequest voucherCreateRequest) {
        try {
            long discount = voucherCreateRequest.getDiscount();
            VoucherType type = voucherCreateRequest.getType();
            LocalDateTime createAt = voucherCreateRequest.getCreateAt() == null ? LocalDateTime.now() : voucherCreateRequest.getCreateAt();

            Voucher voucher = switch (type) {
                case FIXED -> new FixedVoucher(discount);
                case PERCENT -> new RateVoucher(discount);
            };
            return voucherRepository.save(voucher);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
            throw e;
        }
    }

    //조회(Read) - id를 통해서 조회
    public VoucherResponse findById(UUID voucherId) {
        Voucher voucher = voucherRepository.findById(voucherId)
                .orElseThrow(() -> new IllegalArgumentException("조회하시는 Voucher는 존재하지 않습니다."));

        return new VoucherResponse(voucher.getVoucherId(), voucher.getDiscount(), voucher.getVoucherType(), voucher.getCreatedAt());
    }

    //조회(Read) - 생성일을 통해서 조회
    public VoucherResponse findByCreateAt(LocalDateTime createAt) {
        Voucher voucher = voucherRepository.findByCreatedAt(createAt)
                .orElseThrow(() -> new IllegalArgumentException("조회하시는 Voucher는 존재하지 않습니다."));
        return new VoucherResponse(voucher.getVoucherId(), voucher.getDiscount(), voucher.getVoucherType(), voucher.getCreatedAt());
    }

    //조회(Read) - 바우처 타입별로 조회

    public VoucherListResponse findByType(VoucherType type) {
        List<Voucher> ListVoucherType = voucherRepository.findByType(type);
        List<VoucherResponse> voucherResponseList = ListVoucherType.stream()
                .map(voucher -> new VoucherResponse(voucher.getVoucherId(), voucher.getDiscount(),
                        voucher.getVoucherType(), voucher.getCreatedAt()))
                .collect(Collectors.toList());
        return new VoucherListResponse(voucherResponseList);
    }

    //조회(Read) -모든 바우처의 목록을 조회
    public VoucherListResponse findAllVouchers() {
        List<Voucher> vouchers = voucherRepository.findAll();
        List<VoucherResponse> voucherResponseList = vouchers.stream()
                .map(voucher -> new VoucherResponse(voucher.getVoucherId(), voucher.getDiscount(),
                        voucher.getVoucherType(), voucher.getCreatedAt()))
                .collect(Collectors.toList());

        return new VoucherListResponse(voucherResponseList);
    }

    //수정(Update)
    public void updateVoucher(VoucherUpdateRequest voucherUpdateRequest) {
        UUID voucherId = voucherUpdateRequest.getVoucherId();
        Optional<Voucher> storegedVoucher = voucherRepository.findById(voucherId);

        Voucher voucher = storegedVoucher.orElseThrow(() -> new IllegalArgumentException("해당 바우처는 존재하지 않습니다."));

        voucherRepository.update(voucher);


    }

    //삭제(Delete) -id
    public void deleteById(UUID voucherId) {
        Optional<Voucher> deleteByIdVoucher = voucherRepository.deleteById(voucherId);
        deleteByIdVoucher.orElseThrow(() -> new IllegalArgumentException("해당 바우처는 존재하지 않습니다."));
    }

    //삭제(Delete)
    public void deleteAllVoucher() {
        voucherRepository.deleteAll();
    }
}
