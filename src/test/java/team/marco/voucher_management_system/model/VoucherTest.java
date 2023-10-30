package team.marco.voucher_management_system.model;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.text.NumberFormat;
import java.util.UUID;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import team.marco.voucher_management_system.type_enum.VoucherType;

abstract class VoucherTest {
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
}
