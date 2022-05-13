package org.programmers.devcourse.voucher.engine.voucher;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.programmers.devcourse.voucher.engine.voucher.VoucherTestUtil.voucherFixtures;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.programmers.devcourse.voucher.engine.voucher.entity.Voucher;
import org.programmers.devcourse.voucher.engine.voucher.repository.VoucherRepository;

@ExtendWith(MockitoExtension.class)
class DefaultVoucherServiceTest {

  @Mock
  private VoucherRepository repository;

  private DefaultVoucherService voucherService;

  @BeforeEach
  void setup() {
    this.voucherService = new DefaultVoucherService(repository);
  }

  @Test
  @DisplayName("create 메서드가 Voucher를 타입에 맞게 만들고 repository save 메서드를 호출하는가")
  void create() {
    // Given
    var voucher = voucherFixtures.get(0);
    var typeId = VoucherType.FIXED_AMOUNT.getTypeId();
    var discountData = voucher.getDiscountDegree();

    // When
    var createdVoucher = voucherService.create(typeId, discountData);

    // Then
    verify(repository, times(1)).save(any());
    assertThat(createdVoucher.getDiscountDegree()).isEqualTo(discountData);
    assertThat(VoucherType.mapToTypeId(createdVoucher)).isEqualTo(typeId);
  }

  @Test
  void getAllVouchers() {
    // Given
    var storedVouchers = voucherFixtures;
    // When
    when(repository.getAllVouchers()).thenReturn(storedVouchers);

    // Then
    assertThat(voucherService.getAllVouchers()).containsAll(storedVouchers);
  }

  @Test
  void remove_voucher_by_id_calls_voucher_repository_remove_method() {
    // Given
    var voucherId = voucherFixtures.get(0).getVoucherId();
    // When
    voucherService.remove(voucherId);
    // Then
    verify(repository, times(1)).delete(voucherId);
  }

  @Test
  void getVoucherById() {
    // Given
    var voucherId = voucherFixtures.get(0).getVoucherId();
    // When
    when(repository.getVoucherById(voucherId)).thenReturn(Optional.of(voucherFixtures.get(0)));

    // Then
    assertThat(voucherService.getVoucherById(voucherId)).isEqualTo(voucherFixtures.get(0));
  }

  @Test
  void getVouchersByType() {
    // Given
    var fixedAmountVoucher = voucherFixtures.get(0);
    var typeId = VoucherType.FIXED_AMOUNT.getTypeId();
    // When
    when(repository.getVouchersByType(typeId)).thenReturn(List.of(fixedAmountVoucher));

    // Then
    assertThat(voucherService.getVouchersByType(typeId)).containsAll(List.of(fixedAmountVoucher));
  }

  @Test
  @DisplayName("주어진 날짜 이후의 voucher들만 반환해야 한다.")
  void getVouchersByCreatedAt() {
    // Given
    var vouchers = voucherFixtures;
    LocalDateTime targetDate = LocalDate.now().minusDays(1).atStartOfDay(); // 하루 전
    List<Voucher> expectedVouchers = List.of(vouchers.get(2), vouchers.get(3)); // 40분전, 현재
    // When
    when(repository.getAllVouchers()).thenReturn(vouchers);
    var filteredVouchers = voucherService.getVouchersByCreatedAt(targetDate);
    // Then
    assertThat(filteredVouchers).containsAll(expectedVouchers);
  }
}
