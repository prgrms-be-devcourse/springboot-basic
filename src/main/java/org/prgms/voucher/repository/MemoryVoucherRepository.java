package org.prgms.voucher.repository;

import org.prgms.voucher.dto.VoucherResponseDto;
import org.prgms.voucher.voucher.Voucher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Profile("local")
@Repository
public class MemoryVoucherRepository implements VoucherRepository {

    private Map<UUID, Voucher> store = new ConcurrentHashMap<>();

    @Override
    public List<VoucherResponseDto> findAll() {
        return store.values().stream()
                .map(voucher -> VoucherResponseDto
                        .builder()
                        .id(voucher.getId())
                        .amount(voucher.getAmount())
                        .voucherPolicy(voucher.getVoucherPolicy())
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public Voucher save(Voucher voucher) {
        store.put(voucher.getId(), voucher);
        return voucher;
    }

    @Override
    public Optional<VoucherResponseDto> findById(UUID id) {
        Voucher voucher = store.get(id);
        return Optional.ofNullable(VoucherResponseDto.builder()
                .id(voucher.getId())
                .amount(voucher.getAmount())
                .voucherPolicy(voucher.getVoucherPolicy())
                .build()
        );
    }
}
