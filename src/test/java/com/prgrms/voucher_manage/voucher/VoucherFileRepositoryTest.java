package com.prgrms.voucher_manage.voucher;

import com.prgrms.voucher_manage.domain.voucher.entity.PercentDiscountVoucher;
import com.prgrms.voucher_manage.domain.voucher.entity.Voucher;
import com.prgrms.voucher_manage.domain.voucher.repository.FileVoucherRepository;
import com.prgrms.voucher_manage.file.VoucherFileManager;
import org.junit.jupiter.api.*;

import java.util.List;

import static com.prgrms.voucher_manage.domain.voucher.entity.VoucherType.PERCENT;
import static org.assertj.core.api.Assertions.assertThat;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VoucherFileRepositoryTest {
    FileVoucherRepository repository = new FileVoucherRepository(new VoucherFileManager("/src/test/resources/voucher_test.csv"));
    static FileVoucherRepository static_repository = new FileVoucherRepository(new VoucherFileManager("/src/test/resources/voucher_test.csv"));

    @AfterAll()
    public static void saveDummyData(){
        Voucher voucher = new PercentDiscountVoucher(30L);
        static_repository.save(voucher);
    }

    @AfterEach
    public void clear(){
        repository.clear();
    }

    @Test
    @Order(1)
    @DisplayName("파일에 저장된 Voucher를 가져올 수 있다.")
    public void fileVoucherRepository_getFileData(){
        Voucher savedVoucher = repository.findAll().get(0);
        assertThat(savedVoucher.getVoucherType()).isEqualTo(PERCENT);
        assertThat(savedVoucher.getDiscountAmount()).isEqualTo(30L);
    }

    @Test
    @Order(2)
    @DisplayName("Voucher를 저장할 수 있다.")
    public void fileVoucherRepository_save(){
        //given
        Voucher voucher = new PercentDiscountVoucher(10L);
        //when
        Voucher savedVoucher = repository.save(voucher);
        //then
        assertThat(savedVoucher).isNotNull();
        assertThat(savedVoucher.getVoucherType()).isEqualTo(PERCENT);
        assertThat(savedVoucher.getDiscountAmount()).isEqualTo(10L);
        repository.clear();
    }

    @Test
    @Order(3)
    @DisplayName("Voucher 리스트를 반환받을 수 있다.")
    public void fileVoucherRepository_findAll(){
        //given
        Voucher voucher1 = new PercentDiscountVoucher(10L);
        Voucher voucher2 = new PercentDiscountVoucher(20L);

        repository.save(voucher1);
        repository.save(voucher2);

        //when
        List<Voucher> voucherList = repository.findAll();
        //then
        assertThat(voucherList.size()).isEqualTo(2);
        repository.clear();
    }
}
