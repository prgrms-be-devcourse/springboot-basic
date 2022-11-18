package org.prgrms.memory;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.voucher.discountType.DiscountAmount;
import org.prgrms.voucher.discountType.DiscountRate;
import org.prgrms.voucher.voucherType.FixedAmountVoucher;
import org.prgrms.voucher.voucherType.PercentDiscountVoucher;
import org.prgrms.voucher.voucherType.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

@SpringJUnitConfig
@ActiveProfiles("jdbc")
public class VoucherDBMemoryTest extends JdbcBase{

  @Autowired
  VoucherDBMemory memory;

  @BeforeEach
  void clear() {
    memory.deleteAll();
  }

  @DisplayName("바우처를 저장하고 저장한 바우처를 반환한다")
  @Test
  void test1() {
    //given
    Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), new DiscountAmount(300L));
    //when
    Voucher saved = memory.save(voucher);
    //then
    assertEquals(voucher, saved);
  }

  @DisplayName("새로 저장하는 바우처의 id가 이미 있을 경우 에러를 던진다")
  @Test
  void test1_1() {
    //given
    Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), new DiscountAmount(300L));
    memory.save(voucher);
    //when&then
    assertThrows(DuplicateKeyException.class, () -> memory.save(voucher));
  }

  @DisplayName("저장된 모든 바우처의 정보를 반환한다")
  @Test
  void test2() {
    //given
    Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), new DiscountAmount(100L));
    Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), new DiscountRate(10L));
    memory.save(voucher1);
    memory.save(voucher2);
    //when
    List<Voucher> voucherList = memory.findAll();
    //then
    assertEquals(voucherList.size(), 2);
    assertThat(voucherList.get(0)).usingRecursiveComparison().isEqualTo(voucher1);
    assertThat(voucherList.get(1)).usingRecursiveComparison().isEqualTo(voucher2);
  }

  @DisplayName("바우처의 id로 해당 바우처 정보를 가져온다")
  @Test
  void test3() {
    //given
    Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), new DiscountAmount(600L));
    Voucher saved = memory.save(voucher);
    //when
    Optional<Voucher> foundVoucher = memory.findById(saved.getVoucherId());
    //then
    assertThat(saved).usingRecursiveComparison().isEqualTo(foundVoucher.get());
  }

  @DisplayName("바우처의 id로 해당 바우처 정보를 삭제한다")
  @Test
  void test4() {
    //given
    Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), new DiscountAmount(50L));
    Voucher saved = memory.save(voucher);
    //when
    Optional<Voucher> deleted = memory.deleteById(saved.getVoucherId());
    //then
    assertThat(saved).usingRecursiveComparison().isEqualTo(deleted.get());
  }

  @DisplayName("저장된 모든 바우처 정보를 삭제한다")
  @Test
  void test5() {
    //given
    Voucher voucher1 = new FixedAmountVoucher(UUID.randomUUID(), new DiscountAmount(100L));
    Voucher voucher2 = new PercentDiscountVoucher(UUID.randomUUID(), new DiscountRate(10L));
    memory.save(voucher1);
    memory.save(voucher2);
    //when
    memory.deleteAll();
    List<Voucher> all = memory.findAll();
    //then
    assertEquals(all.size(), 0);
  }

}
