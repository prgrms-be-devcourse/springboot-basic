package com.prgrms.springbootbasic.voucher.storage;

import com.prgrms.springbootbasic.voucher.VoucherType;
import com.prgrms.springbootbasic.voucher.domain.FixedAmountVoucher;
import com.prgrms.springbootbasic.voucher.domain.Voucher;
import org.junit.jupiter.api.*;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class FileVoucherStorageTest {

    private static final String CLASSPATH_VOUCHER_STORAGE_TEST = "classpath:voucher_storage_test.txt";
    private static final String ROOT_PATH = System.getProperty("user.dir");
    private static final String VOUCHER_STORAGE_TEST_PATH = "\\out\\test\\resources\\voucher_storage_test.txt";

    private final FileVoucherStorage fileVoucherStorage;
    private final ResourceLoader resourceLoader;

    private Voucher voucher;
    private List<Voucher> vouchers;

    public FileVoucherStorageTest() {
        this.resourceLoader = new DefaultResourceLoader();
        this.fileVoucherStorage = new FileVoucherStorage(
                resourceLoader,
                CLASSPATH_VOUCHER_STORAGE_TEST);
    }

    @BeforeEach
    public void initVoucher() throws IOException {

        File file = new File(ROOT_PATH + VOUCHER_STORAGE_TEST_PATH);
        file.delete();

        FixedAmountVoucher voucher1 = new FixedAmountVoucher(10, VoucherType.FIXED_AMOUNT);
        FixedAmountVoucher voucher2 = new FixedAmountVoucher(15, VoucherType.PERCENT);
        FixedAmountVoucher voucher3 = new FixedAmountVoucher(20, VoucherType.FIXED_AMOUNT);
        voucher = voucher1;
        vouchers = List.of(voucher1, voucher2, voucher3);
        file.createNewFile();
    }

    @Test
    @Order(1)
    @DisplayName("Voucher를 관리하는 파일을 열 수 있다.")
    public void openFile() {
        //given&when
        File openFile = ReflectionTestUtils.invokeMethod(fileVoucherStorage, "openFile");

        //then
        assertThat(Objects.requireNonNull(openFile).getPath())
                .isEqualTo(ROOT_PATH + VOUCHER_STORAGE_TEST_PATH);
    }

    @Test
    @Order(2)
    @DisplayName("Voucher를 저장할 수 있다")
    public void save() throws IOException {

        //given&when
        fileVoucherStorage.save(voucher);
        Resource resource = resourceLoader.getResource(CLASSPATH_VOUCHER_STORAGE_TEST);
        File file = resource.getFile();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String readLine = bufferedReader.readLine();
            List<String> columns = List.of(readLine.split(" "));

            //then
            assertThat(voucher.getUUID().toString()).isEqualTo(columns.get(0));
            assertThat(voucher.getVoucherType()).isEqualTo(VoucherType.fromInputValue(columns.get(1)));
            assertThat(voucher.getDiscountRate()).isEqualTo(Integer.parseInt(columns.get(2)));
        }
    }

    @Test
    @Order(3)
    @DisplayName("voucher_id로 Voucher 정보를 조회할 수 있다.")
    public void findById() {
        //given
        fileVoucherStorage.save(voucher);

        //when
        Optional<Voucher> found = fileVoucherStorage.findById(voucher.getUUID());

        //then
        assertThat(found.isPresent())
                .isTrue();
        Voucher foundVoucher = found.get();
        assertThat(foundVoucher)
                .usingRecursiveComparison()
                .isEqualTo(voucher);
    }

    @Test
    @Order(4)
    @DisplayName("모든 Voucher 정보를 조회할 수 있다.")
    public void findAll() {
        //given
        vouchers.forEach(fileVoucherStorage::save);

        //when
        List<Voucher> foundVouchers = fileVoucherStorage.findAll();

        //then
        assertThat(vouchers.size()).isEqualTo(foundVouchers.size());
    }
}