package prgms.springbasic;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import prgms.springbasic.voucher.VoucherService;
import prgms.springbasic.voucher.VoucherType;

import java.io.IOException;
import java.util.UUID;

public class AutoAppConfigTest {
    @Test
    void autoScanTest() throws IOException {
        ApplicationContext ac = new AnnotationConfigApplicationContext(CommandLineAppConfig.class);
        VoucherService voucherService = ac.getBean(VoucherService.class);
        voucherService.createVoucher(VoucherType.FIXEDAMOUNTVOUCHER, UUID.randomUUID(), "2000");
        Assertions.assertThat(voucherService.getVoucherList().size()).isEqualTo(1);
    }
}
