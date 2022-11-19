package prgms.vouchermanagementapp.voucher.warehouse;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Profile;
import prgms.vouchermanagementapp.VoucherManagementApp;
import prgms.vouchermanagementapp.domain.Voucher;
import prgms.vouchermanagementapp.domain.model.Ratio;
import prgms.vouchermanagementapp.storage.Vouchers;
import prgms.vouchermanagementapp.voucher.VoucherCreationFactory;

import java.io.File;

@Profile("release")
class FileVouchersTest {

    private static final String FILE_PATH = "src/main/resources/";

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(VoucherManagementApp.class);
    Vouchers vouchers = applicationContext.getBean(Vouchers.class);

    @DisplayName("파일이 지정한 위치에 정상 생성 되는지 확인")
    @Test
    void testMethodNameHere() {
        // given
        File file = new File(FILE_PATH, "file_vouchers.txt");

        // when
        Voucher voucher = VoucherCreationFactory.createVoucher(new Ratio(3));
        vouchers.store(voucher);

        // then
        Assertions.assertThat(file.exists()).isTrue();
    }
}