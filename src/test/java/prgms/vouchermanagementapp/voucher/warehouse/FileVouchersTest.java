package prgms.vouchermanagementapp.voucher.warehouse;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import prgms.vouchermanagementapp.model.Ratio;
import prgms.vouchermanagementapp.voucher.VoucherCreationFactory;
import prgms.vouchermanagementapp.voucher.model.Voucher;

import java.io.File;

class FileVouchersTest {

    private static final String FILE_PATH = "src/main/resources/";

    VoucherWarehouse voucherWarehouse = new FileVouchers();

    @DisplayName("파일이 지정한 위치에 정상 생성 되는지 확인")
    @Test
    void testMethodNameHere() {
        // given
        File file = new File(FILE_PATH, "file_vouchers.txt");

        // when
        Voucher voucher = VoucherCreationFactory.createVoucher(new Ratio(3));
        voucherWarehouse.store(voucher);

        // then
        Assertions.assertThat(file.exists()).isTrue();
    }
}