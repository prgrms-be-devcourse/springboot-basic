package com.programmers.voucher.domain.voucher.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.programmers.voucher.domain.voucher.model.Voucher;
import com.programmers.voucher.domain.voucher.model.VoucherType;
import com.programmers.voucher.domain.voucher.repository.VoucherRepository;
import com.programmers.voucher.domain.voucher.util.VoucherFactory;
import com.programmers.voucher.web.voucher.dto.VoucherRequestDto;
import com.programmers.voucher.web.voucher.dto.VoucherResponseDto;

@Service
public class VoucherWebService {

	private final VoucherRepository repository;

	public VoucherWebService(VoucherRepository repository) {
		this.repository = repository;
	}

	public VoucherResponseDto createVoucher(VoucherRequestDto voucherRequestDto) {
		Voucher voucher = convertDtoToVoucher(voucherRequestDto);
		repository.save(voucher);
		VoucherResponseDto voucherResponseDto = convertVoucherToDto(voucher);
		return voucherResponseDto;
	}

	public VoucherResponseDto findById(UUID voucherId) {
		Voucher voucher = repository.findById(voucherId);
		VoucherResponseDto voucherResponseDto = convertVoucherToDto(voucher);
		return voucherResponseDto;
	}

	public List<VoucherResponseDto> getVouchersBy(
		VoucherType voucherType,
		LocalDateTime startTime,
		LocalDateTime endTime
	) {
		return repository.findBy(voucherType, startTime, endTime)
			.stream()
			.map(this::convertVoucherToDto)
			.collect(Collectors.toList());
	}

	public List<VoucherResponseDto> getAllVoucher() {
		List<Voucher> vouchers = repository.findAll();
		List<VoucherResponseDto> voucherResponseDtoList = new ArrayList<>();
		vouchers.stream().
			forEach(voucher -> {
				VoucherResponseDto voucherResponseDto = convertVoucherToDto(voucher);
				voucherResponseDtoList.add(voucherResponseDto);
			});
		return voucherResponseDtoList;
	}

	public void removeVoucher(UUID voucherId) {
		repository.deleteById(voucherId);
	}

	private Voucher convertDtoToVoucher(VoucherRequestDto voucherRequestDto) {
		return VoucherFactory.createVoucher(voucherRequestDto.getVoucherType(), voucherRequestDto.getDiscount());
	}

	private VoucherResponseDto convertVoucherToDto(Voucher voucher) {
		return new VoucherResponseDto(voucher.getVoucherId(), voucher.getDiscount(),
			voucher.getVoucherType(),
			voucher.getCreatedAt());
	}
}
