package com.example.commandlineapplication.domain.voucher.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.example.commandlineapplication.domain.voucher.Voucher;
import com.example.commandlineapplication.domain.voucher.VoucherType;
import com.example.commandlineapplication.domain.voucher.dto.mapper.VoucherMapper;
import com.example.commandlineapplication.domain.voucher.dto.request.VoucherCreateRequest;
import com.example.commandlineapplication.domain.voucher.dto.response.VoucherResponse;
import com.example.commandlineapplication.domain.voucher.repository.VoucherRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

  private VoucherService voucherService;
  private VoucherMapper voucherMapper;
  private VoucherFactory voucherFactory;
  @Mock
  private VoucherRepository voucherRepository;

  @BeforeEach
  void init() {
    voucherService = new VoucherService(voucherRepository, new VoucherFactory(),
        new VoucherMapper());
    voucherMapper = new VoucherMapper();
    voucherFactory = new VoucherFactory();
  }

  @ParameterizedTest
  @CsvSource({
      "1", "10", "100"
  })
  @DisplayName("FixedAmountVoucher 저장 성공")
  void createFixedAmountVoucher(int amount) {

    //given
    VoucherType voucherType = VoucherType.FIXED;
    Voucher voucher = voucherFactory.create(
        VoucherCreateRequest.builder()
            .voucherId(UUID.randomUUID())
            .voucherType(VoucherType.FIXED)
            .discountAmount(amount)
            .build());

    given(voucherRepository.insert(any())).willReturn(voucher);

    //when
    voucherService.createVoucher(voucherType, amount);

    //then
    then(voucherRepository).should().insert(any());
  }

  @ParameterizedTest
  @CsvSource({
      "1", "10", "100"
  })
  @DisplayName("PercentDiscountVoucher 저장 성공")
  void createPercentDiscountVoucher(int amount) {

    //given
    VoucherType voucherType = VoucherType.PERCENT;
    Voucher voucher = voucherFactory.create(
        VoucherCreateRequest.builder()
            .voucherId(UUID.randomUUID())
            .voucherType(VoucherType.PERCENT)
            .discountAmount(amount)
            .build());

    given(voucherRepository.insert(any())).willReturn(voucher);

    //when
    voucherService.createVoucher(voucherType, amount);

    //then
    then(voucherRepository).should().insert(any());
  }

  @Test
  @DisplayName("Voucher 목록 조회 성공")
  void getVouchers() {

    //given
    Voucher voucher1 = voucherFactory.create(
        VoucherCreateRequest.builder()
            .voucherId(UUID.randomUUID())
            .voucherType(VoucherType.PERCENT)
            .discountAmount(10)
            .build());

    Voucher voucher2 = voucherFactory.create(
        VoucherCreateRequest.builder()
            .voucherId(UUID.randomUUID())
            .voucherType(VoucherType.FIXED)
            .discountAmount(10)
            .build());

    List<Voucher> voucherList = new ArrayList<>();
    voucherList.add(voucher1);
    voucherList.add(voucher2);

    given(voucherRepository.findAll()).willReturn(voucherList);

    //when
    List<VoucherResponse> foundVoucherList = voucherService.findVouchers();

    //then
    List<VoucherResponse> voucherResponseList = voucherList.stream()
        .map(voucherMapper::voucherToResponse)
        .collect(Collectors.toList());

    assertThat(foundVoucherList).usingRecursiveFieldByFieldElementComparator()
        .isEqualTo(voucherResponseList);
  }

  @Test
  @DisplayName("Voucher 삭제 성공")
  void deleteVoucher() {

    //given
    Voucher voucher = voucherFactory.create(
        VoucherCreateRequest.builder()
            .voucherId(UUID.randomUUID())
            .voucherType(VoucherType.PERCENT)
            .discountAmount(10)
            .build());

    given(voucherRepository.findById(any())).willReturn(Optional.of(voucher));

    //when
    voucherService.deleteVoucher(voucher.getVoucherId());

    //then
    then(voucherRepository).should().deleteById(any());
  }
}