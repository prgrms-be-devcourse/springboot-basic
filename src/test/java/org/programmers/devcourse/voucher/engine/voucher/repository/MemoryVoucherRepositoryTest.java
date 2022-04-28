package org.programmers.devcourse.voucher.engine.voucher.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.programmers.devcourse.voucher.engine.voucher.VoucherTestUtil.voucherFixtures;

import java.time.LocalDateTime;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.programmers.devcourse.voucher.engine.voucher.entity.PercentDiscountVoucher;

class MemoryVoucherRepositoryTest {

  private static final MemoryVoucherRepository repository = new MemoryVoucherRepository();

  @BeforeEach
  void setup() {
    repository.deleteAll();
    voucherFixtures.forEach(repository::save);
  }

  @Test
  @DisplayName("repository에 바우처를 저장할 수 있다.")
  void save() {
    var newVoucher = PercentDiscountVoucher.factory.create(UUID.randomUUID(), 30, LocalDateTime.now());
    repository.save(newVoucher);
    assertThat(repository.getVoucherById(newVoucher.getVoucherId())).isNotEmpty().get().isEqualTo(newVoucher);
  }

  @Test
  @DisplayName("repository를 id를 통해 검색할 수 있다.")
  void getVoucherById() {
    assertThat(repository.getVoucherById(voucherFixtures.get(0).getVoucherId())).isNotEmpty().get().isEqualTo(voucherFixtures.get(0));
  }

  @Test
  @DisplayName("저장된 모든 바우처를 불러올 수 있어야 한다.")
  void getAllVouchers() {
    assertThat(repository.getAllVouchers()).containsAll(voucherFixtures);
  }

  @Test
  @DisplayName("저장된 모든 바우처를 삭제할 수 있어야 한다.")
  void deleteAll() {
    var removedCount = repository.deleteAll();
    assertThat(removedCount).isEqualTo(voucherFixtures.size());
    assertThat(repository.getAllVouchers()).isEmpty();
  }

  @Test
  @DisplayName("원하는 바우처를 삭제할 수 있어야 한다.")
  void delete() {
    var target = voucherFixtures.get(0);
    repository.delete(target.getVoucherId());
    assertThat(repository.getVoucherById(target.getVoucherId())).isEmpty();
    assertThat(repository.getAllVouchers()).hasSize(voucherFixtures.size() - 1);
  }
}
