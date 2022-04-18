package org.programmers.devcourse.voucher.engine.voucher.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.programmers.devcourse.voucher.engine.exception.VoucherDiscountDegreeOutOfRangeException;
import org.programmers.devcourse.voucher.engine.exception.VoucherException;
import org.programmers.devcourse.voucher.engine.voucher.entity.FixedAmountVoucher;
import org.programmers.devcourse.voucher.engine.voucher.entity.PercentDiscountVoucher;
import org.programmers.devcourse.voucher.engine.voucher.entity.Voucher;

class FileVoucherRepositoryTest {


  static Stream<Arguments> voucherSource() throws VoucherDiscountDegreeOutOfRangeException {
    return Stream.of(arguments(List.of(
        FixedAmountVoucher.factory.create(UUID.randomUUID(), 10000L),
        PercentDiscountVoucher.factory.create(UUID.randomUUID(), 50),
        PercentDiscountVoucher.factory.create(UUID.randomUUID(), 75),
        PercentDiscountVoucher.factory.create(UUID.randomUUID(), 11)
    )));
  }

  @ParameterizedTest
  @DisplayName("초기화 시에 FileChannel에 저장된 Voucher를 정확하게 가져와야 한다.")
  @MethodSource("voucherSource")
  void should_get_proper_vouchers_from_file_channel(
      List<Voucher> incomingVouchers) {
    // given
    var fileChannelMock = mock(FileChannel.class);
    var serializedVouchers = incomingVouchers.stream().map(FileVoucherRepository.serializer)
        .toArray(String[]::new);

    // when
    when(fileChannelMock.readAllLines()).thenReturn(
        serializedVouchers
    );
    var repository = new FileVoucherRepository(fileChannelMock);

    // then
    var storedVouchers = repository.getAllVouchers();
    assertThat(storedVouchers).isNotEmpty().allMatch(incomingVouchers::contains);

  }

  @ParameterizedTest
  @DisplayName("save를 호출하면 voucher가 정확히 저장되어야 한다.")
  @MethodSource("voucherSource")
  void save_proper_voucher(List<Voucher> incomingVouchers) throws VoucherException {

    //given
    var fileChannelMock = mock(FileChannel.class);
    when(fileChannelMock.readAllLines()).thenReturn(new String[0]);
    var repository = new FileVoucherRepository(fileChannelMock);

    //when
    for (Voucher voucher : incomingVouchers) {
      repository.save(voucher);
    }

    //then
    var storedVouchers = repository.getAllVouchers();
    assertThat(storedVouchers).isNotEmpty().allMatch(incomingVouchers::contains);
  }

  @Test
  @DisplayName("저장한 바우처가 없을 경우 비어 있는 컬렉션을 반환해야 한다.")
  void return_empty_collection_when_no_voucher_is_saved()
      throws VoucherException {

    //given
    var fileChannelMock = mock(FileChannel.class);
    when(fileChannelMock.readAllLines()).thenReturn(new String[0]);
    var repository = new FileVoucherRepository(fileChannelMock);

    //when
    var storedVouchers = repository.getAllVouchers();

    //then
    assertThat(storedVouchers).isEmpty();
  }


  @ParameterizedTest
  @DisplayName("voucherId로 원하는 voucher를 가져올 수 있어야 한다.")
  @MethodSource("voucherSource")
  void get_proper_voucher_by_voucher_id(List<Voucher> incomingVouchers) throws VoucherException {

    //given
    var fileChannelMock = mock(FileChannel.class);
    when(fileChannelMock.readAllLines()).thenReturn(new String[0]);
    var repository = new FileVoucherRepository(fileChannelMock);

    //when
    for (Voucher voucher : incomingVouchers) {
      repository.save(voucher);
    }

    //then
    incomingVouchers.forEach(voucher -> {
      var queriedVoucher = repository.getVoucherById(voucher.getVoucherId());
      assertThat(queriedVoucher).isNotEmpty();
      assertThat(queriedVoucher).contains(voucher);
    });
  }
}
