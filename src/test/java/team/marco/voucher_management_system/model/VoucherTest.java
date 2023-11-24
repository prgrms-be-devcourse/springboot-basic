package team.marco.voucher_management_system.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import team.marco.voucher_management_system.type_enum.VoucherType;

abstract class VoucherTest {
    abstract protected VoucherType getTargetType();

    abstract protected int generateValidData();

    abstract protected Voucher generateVoucher(int data);

    @Test
    @DisplayName("쿠폰의 타입은 null일 수 없고 VoucherType에 정의되야 한다.")
    void testGetType() {
        // given
        Voucher voucher = generateVoucher(generateValidData());

        // when
        VoucherType voucherType = voucher.getType();

        // then
        assertThat(voucherType).isNotNull();
        assertThat(voucherType).isInstanceOf(VoucherType.class);
    }

    @Test
    @DisplayName("쿠폰 생성 시 주어진 data와 동일한 쿠폰이 생성되야 한다.")
    void testGetData() {
        // given
        int initData = generateValidData();
        Voucher voucher = generateVoucher(initData);

        // when
        int voucherData = voucher.getData();

        // then
        assertThat(voucherData).isEqualTo(initData);
    }

    @Test
    @DisplayName("쿠폰 정보에는 ID 정보와 주어진 data의 콤마 포맷 정보가 포함되야 한다.")
    void testGetInfo() {
        // given
        int initData = generateValidData();
        Voucher voucher = generateVoucher(initData);

        // when
        String voucherInfo = voucher.getInfo();

        // then
        UUID id = voucher.getId();

        assertThat(voucherInfo).contains(id.toString());
        assertThat(voucherInfo).contains(NumberFormat.getIntegerInstance().format(initData));
    }

    @Nested
    @DisplayName("id 판별 테스트")
    class TestIsSameId {
        @Test
        @DisplayName("동일한 id일 경우 true를 반환한다.")
        void isSameId() {
            // given
            Voucher voucher = generateVoucher(generateValidData());
            UUID voucherId = voucher.getId();

            // when
            boolean isSameId = voucher.isSameId(voucherId);

            // then
            assertThat(isSameId).isTrue();
        }

        @Test
        @DisplayName("동일하지 않은 id일 경우 false를 반환한다.")
        void isNotSameId() {
            // given
            Voucher voucher = generateVoucher(generateValidData());
            Voucher otherVoucher = generateVoucher(generateValidData());
            UUID voucherId = otherVoucher.getId();

            // when
            boolean isSameId = voucher.isSameId(voucherId);

            // then
            assertThat(isSameId).isFalse();
        }
    }

    @Nested
    @DisplayName("type 판별 테스트")
    class TestIsSameType {
        @Test
        @DisplayName("동일한 type일 경우 true를 반환한다.")
        void isSameType() {
            // given
            Voucher voucher = generateVoucher(generateValidData());
            VoucherType targetType = getTargetType();

            // when
            boolean isSameType = voucher.isSameType(targetType);

            // then
            assertThat(isSameType).isTrue();
        }

        @Test
        @DisplayName("동일하지 않은 type일 경우 false를 반환한다.")
        void isNotSameType() {
            // given
            Voucher voucher = generateVoucher(generateValidData());
            VoucherType targetType = getTargetType();
            VoucherType otherType = Arrays.stream(VoucherType.values())
                    .filter(voucherType -> voucherType != targetType)
                    .findFirst()
                    .orElseThrow();

            // when
            boolean isSameType = voucher.isSameType(otherType);

            // then
            assertThat(isSameType).isFalse();
        }
    }

    @Test
    void isCreatedBetween() {
        // given
        List<Voucher> vouchers = List.of(
                generateVoucher(generateValidData()),
                generateVoucher(generateValidData()),
                generateVoucher(generateValidData()),
                generateVoucher(generateValidData()),
                generateVoucher(generateValidData()));

        LocalDateTime from = vouchers.get(1).getCreateAt();
        LocalDateTime to = vouchers.get(3).getCreateAt();

        // when
        List<UUID> actualVoucherIds = vouchers.stream()
                .filter(voucher -> voucher.isCreatedBetween(from, to))
                .map(Voucher::getId)
                .toList();

        // then
        List<UUID> expectedVoucherIds = Stream.of(vouchers.get(1), vouchers.get(2), vouchers.get(3))
                .map(Voucher::getId)
                .toList();

        assertThat(actualVoucherIds).isEqualTo(expectedVoucherIds);
    }
}
