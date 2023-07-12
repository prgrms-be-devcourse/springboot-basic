package org.devcourse.springbasic.domain.voucher.dao;

import org.devcourse.springbasic.domain.voucher.domain.Voucher;
import org.devcourse.springbasic.domain.voucher.domain.VoucherType;
import org.devcourse.springbasic.domain.voucher.dto.VoucherDto;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Profile("local")
@Repository
public class VoucherMemoryRepository implements VoucherRepository {

    private final Map<UUID, Voucher> storage = new ConcurrentHashMap<>();

    @Override
    public VoucherDto.ResponseDto findById(UUID voucherId) {
        Optional<Voucher> voucher = Optional.ofNullable(storage.get(voucherId));
        return voucher.map(v -> new VoucherDto.ResponseDto(v.getVoucherId(), v.getVoucherType(), v.getDiscountRate()))
                .orElseThrow(() -> new IllegalArgumentException("해당 아이디인 바우처가 존재하지 않습니다."));
    }

    @Override
    public List<VoucherDto.ResponseDto> findAll() {
        return storage.values().stream()
                .map(voucher -> new VoucherDto.ResponseDto(voucher.getVoucherId(), voucher.getVoucherType(), voucher.getDiscountRate()))
                .collect(Collectors.toList());
    }

    @Override
    public UUID save(VoucherDto.SaveRequestDto voucherDto) {
        VoucherType voucherType = voucherDto.getVoucherType();
        Voucher voucher = voucherType.getVoucherSupplier().get();
        storage.put(voucher.getVoucherId(), voucher);

        return voucher.getVoucherId();
    }
}
