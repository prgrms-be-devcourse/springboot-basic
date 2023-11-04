package org.programmers.springboot.basic.domain.voucher.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherRequestDto;
import org.programmers.springboot.basic.domain.voucher.dto.VoucherResponseDto;
import org.programmers.springboot.basic.domain.voucher.entity.Voucher;
import org.programmers.springboot.basic.domain.voucher.entity.VoucherType;
import org.programmers.springboot.basic.domain.voucher.exception.DuplicateVoucherException;
import org.programmers.springboot.basic.domain.voucher.exception.VoucherNotFoundException;
import org.programmers.springboot.basic.domain.voucher.mapper.VoucherMapper;
import org.programmers.springboot.basic.util.generator.DateTimeGenerator;
import org.programmers.springboot.basic.util.generator.UUIDGenerator;
import org.programmers.springboot.basic.domain.voucher.repository.VoucherRepository;
import org.programmers.springboot.basic.domain.voucher.service.validate.VoucherValidationStrategy;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Profile("default")
@Slf4j
@Service
@RequiredArgsConstructor
public class VoucherService {

    private final VoucherRepository voucherRepository;
    private final UUIDGenerator uuidGenerator;
    private final DateTimeGenerator dateTimeGenerator;
    private final Map<VoucherType, VoucherValidationStrategy> validationStrategyMap;

    @Transactional
    public void create(VoucherRequestDto voucherRequestDto) {

        Long discount = voucherRequestDto.getDiscount();
        VoucherType voucherType = voucherRequestDto.getVoucherType();

        Voucher voucher = VoucherMapper.INSTANCE.mapToEntityWithGenerator(voucherRequestDto,
                                                                        uuidGenerator,
                                                                        dateTimeGenerator,
                                                                        validationStrategyMap);
        voucher.validate();
        if (isDuplicate(voucher)) {
            log.warn("voucher of voucherType '{}' and discount '{}' is already exists", voucherType, discount);
            throw new DuplicateVoucherException();
        }
        voucherRepository.save(voucher);
    }

    private boolean isDuplicate(Voucher voucher) {
        return voucherRepository.findByTypeNDiscount(voucher.getVoucherType(), voucher.getDiscount()).isPresent();
    }

    public List<VoucherResponseDto> findAll() {

        List<Voucher> vouchers = getAll();
        return vouchers.stream()
                .map(VoucherMapper.INSTANCE::mapToResponseDto)
                .toList();
    }

    public VoucherResponseDto findById(VoucherRequestDto voucherRequestDto) {

        UUID voucherId = voucherRequestDto.getVoucherId();

        Voucher voucher = get(voucherId);
        return VoucherMapper.INSTANCE.mapToResponseDto(voucher);
    }

    public List<VoucherResponseDto> findByOption(VoucherRequestDto voucherRequestDto) {

        UUID voucherId = voucherRequestDto.getVoucherId();
        VoucherType voucherType = voucherRequestDto.getVoucherType();
        Long discount = voucherRequestDto.getDiscount();
        LocalDateTime createdAt = voucherRequestDto.getCreatedAt();
        List<Voucher> vouchers = getByOption(voucherId, voucherType, discount, createdAt);
        return vouchers.stream()
                .map(VoucherMapper.INSTANCE::mapToResponseDto)
                .toList();
    }

    @Transactional
    public void deleteVoucher(VoucherRequestDto voucherRequestDto) {
        VoucherResponseDto voucherResponseDto = findById(voucherRequestDto);
        delete(voucherResponseDto.getVoucherId());
    }

    @Transactional
    public void updateVoucher(VoucherRequestDto voucherRequestDto) {
        Voucher voucher = VoucherMapper.INSTANCE.mapToEntity(voucherRequestDto);
        voucherRepository.update(voucher);
    }

    private Voucher get(UUID voucherId) {
        return voucherRepository.findById(voucherId)
                .orElseThrow(() -> {
                    log.warn("No voucher found for voucherId: {}" , voucherId);
                    return new VoucherNotFoundException();
                });
    }

    private List<Voucher> getByOption(UUID voucherId,
                                      VoucherType voucherType,
                                      Long discount,
                                      LocalDateTime createdAt) {
        return voucherRepository.find(voucherId, voucherType, discount, createdAt);
    }

    private List<Voucher> getAll() {
        return voucherRepository.findAll();
    }

    private void delete(UUID voucherId) {
        voucherRepository.delete(voucherId);
    }

    @Transactional
    public void deleteAll() {
        voucherRepository.deleteAll();
    }
}
