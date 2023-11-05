package team.marco.voucher_management_system.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.text.NumberFormat;
import java.util.Arrays;
import java.util.UUID;
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
        assertThat(voucherType, notNullValue());
        assertThat(voucherType, instanceOf(VoucherType.class));
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
        assertThat(voucherData, is(initData));
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

        assertThat(voucherInfo, containsString(id.toString()));
        assertThat(voucherInfo, containsString(NumberFormat.getIntegerInstance().format(initData)));
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
            assertThat(isSameId, is(true));
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
            assertThat(isSameId, is(false));
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
            assertThat(isSameType, is(true));
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
            assertThat(isSameType, is(false));
        }
    }
}
