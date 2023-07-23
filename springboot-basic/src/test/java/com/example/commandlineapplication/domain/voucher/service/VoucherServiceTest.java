package com.example.commandlineapplication.domain.voucher.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.example.commandlineapplication.domain.voucher.Voucher;
import com.example.commandlineapplication.domain.voucher.VoucherType;
import com.example.commandlineapplication.domain.voucher.dto.mapper.VoucherMapper;
import com.example.commandlineapplication.domain.voucher.dto.request.VoucherCreateRequest;
import com.example.commandlineapplication.domain.voucher.repository.VoucherRepository;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

  private VoucherService voucherService;
  @Mock
  private VoucherRepository voucherRepository; // DB 접근 계층과 연결을 수행하지 않고 stub해준다. 단위테스트에 대한 영역에 반환값에 대한 설정 voucherFactory, voucherMapper는 실제 객체 사용

  @BeforeEach
  void init() {
    voucherService = new VoucherService(voucherRepository, new VoucherFactory(),
        new VoucherMapper());
  }

  @ParameterizedTest
  @CsvSource({
      "1", "10", "100"
  })
  @DisplayName("Voucher 저장 성공")
  void createFixedAmountVoucher(int amount) {
    
    //given
    VoucherType voucherType = VoucherType.FIXED;
    VoucherFactory voucherFactory = new VoucherFactory();
    Voucher voucher = voucherFactory.create(
        VoucherCreateRequest.builder()
            .voucherId(UUID.randomUUID())
            .voucherType(VoucherType.FIXED)
            .discountAmount(amount)
            .build());

    given(voucherRepository.insert(any())).willReturn(voucher); // 무조건 바우처라는 객체를 반환할 것이다.

    //when
    voucherService.createVoucher(voucherType, amount);

    //then
    then(voucherRepository).should().insert(any());
  }
}