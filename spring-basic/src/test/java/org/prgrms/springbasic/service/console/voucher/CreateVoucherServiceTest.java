package org.prgrms.springbasic.service.console.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbasic.utils.io.console.Console;
import org.prgrms.springbasic.domain.voucher.VoucherType;
import org.prgrms.springbasic.repository.voucher.MemoryVoucherRepository;
import org.prgrms.springbasic.service.console.ConsoleService;

import java.lang.reflect.InvocationTargetException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.prgrms.springbasic.domain.voucher.VoucherType.FIXED;

class CreateVoucherServiceTest {

    MemoryVoucherRepository repository = new MemoryVoucherRepository();
    ConsoleService service = new CreateVoucherService(repository, new Console());

    @Test
    @DisplayName("커맨드 입력이 fixed, percent 일 경우 바우처 타입을 반환해야 한다.")
    void testValidateVoucherType() throws Exception {
        var validateFixedType = service.getClass().getDeclaredMethod("validateVoucherType", String.class);
        validateFixedType.setAccessible(true);

        var fixed = (VoucherType) validateFixedType.invoke(service, "fixed");
        assertThat(fixed, instanceOf(VoucherType.class));
        assertThat(fixed.getValue(), is("fixed"));

        var validatePercentType = service.getClass().getDeclaredMethod("validateVoucherType", String.class);
        validatePercentType.setAccessible(true);

        var percent = (VoucherType) validatePercentType.invoke(service, "percent");
        assertThat(percent, instanceOf(VoucherType.class));
        assertThat(percent.getValue(), is("percent"));
    }

    @Test
    @DisplayName("커맨드 입력이 잘못될 경우 예외가 발생해야한다..")
    void testValidateVoucherTypeException() throws Exception {
        var validateFixedType = service.getClass().getDeclaredMethod("validateVoucherType", String.class);
        validateFixedType.setAccessible(true);

        //왜 InvocationTargetException 가 발생하는지 모르겠음...
        assertThrows(InvocationTargetException.class, () -> validateFixedType.invoke(service, "not_command"));
    }

    @Test
    @DisplayName("입력되는 문자를 long 타입으로 파싱을 해야한다.")
    void testValidateDiscountInfo() throws Exception {
        var validateDiscountInfo = service.getClass().getDeclaredMethod("validateDiscountInfo", String.class);
        validateDiscountInfo.setAccessible(true);

        var discountInfo = validateDiscountInfo.invoke(service, "1000");

        assertThat(discountInfo, instanceOf(long.class));
        assertThat(discountInfo, is(1000L));
    }

    @Test
    @DisplayName("long 타입으로 파싱을 할 수 없으면 예외 발생")
    void testValidateDiscountInfoException() throws Exception {
        var validateDiscountInfo = service.getClass().getDeclaredMethod("validateDiscountInfo", String.class);
        validateDiscountInfo.setAccessible(true);

        assertThrows(InvocationTargetException.class, () -> validateDiscountInfo.invoke(service, "abc"));
    }

    @Test
    @DisplayName("잘못된 커맨드가 아니라면 바우처를 생성해야 한다.")
    void testCreateVoucher() throws Exception {
        var createVoucher = service.getClass().getDeclaredMethod("createVoucher", VoucherType.class, long.class);
        createVoucher.setAccessible(true);

        createVoucher.invoke(service, FIXED, 1000);

        assertThat(repository.countStorageSize(), is(1));
    }
}