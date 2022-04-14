package org.prgrms.springbasic.service.console.voucher;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.prgrms.springbasic.domain.console.Console;
import org.prgrms.springbasic.repository.voucher.MemoryVoucherRepository;
import org.prgrms.springbasic.repository.voucher.VoucherRepository;
import org.prgrms.springbasic.service.console.ConsoleService;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

class ListVoucherServiceTest {

    VoucherRepository repository = new MemoryVoucherRepository();
    ConsoleService service = new ListVoucherService(repository, new Console());

    @Test
    @DisplayName("바우처가 존재하지 않는 경우 예외가 발생해야한다.")
    void testValidateVouchers() throws Exception {
        var validateVouchers = service.getClass().getDeclaredMethod("validateVouchers", List.class);
        validateVouchers.setAccessible(true);

        assertThrows(InvocationTargetException.class, () -> validateVouchers.invoke(service, repository.findAll()));
    }
}