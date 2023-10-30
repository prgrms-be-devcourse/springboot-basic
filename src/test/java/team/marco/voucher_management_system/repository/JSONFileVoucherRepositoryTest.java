package team.marco.voucher_management_system.repository;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.samePropertyValuesAs;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.List;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import team.marco.voucher_management_system.configuration.PropertyConfiguration;
import team.marco.voucher_management_system.model.Voucher;
import team.marco.voucher_management_system.properties.FilePathProperties;

@SpringBootTest(classes = PropertyConfiguration.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
class JSONFileVoucherRepositoryTest extends VoucherRepositoryTest {
    private static final String SETUP_DATA;

    static {
        SETUP_DATA = "[{\"id\": \"a5124642-c6f4-4f22-9e2b-9d25f98499d4\",\"type\": \"FIXED\",\"data\": 10000}]";
    }

    @Autowired
    private FilePathProperties filePathProperties;

    @Override
    protected VoucherRepository getRepository() {
        return getJSONFileRepository();
    }

    private JSONFileVoucherRepository getJSONFileRepository() {
        return new JSONFileVoucherRepository(filePathProperties);
    }

    @BeforeAll
    void setup() {
        try (FileWriter fileWriter = new FileWriter(filePathProperties.voucherData())) {
            fileWriter.write(SETUP_DATA);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Test
    @Order(Integer.MIN_VALUE)
    @DisplayName("레포지토리 생성 시 이전에 저장된 데이터를 로드해야한다.")
    void testLoad() {
        // given

        // when
        JSONFileVoucherRepository repository = getJSONFileRepository();

        // then
        List<Voucher> vouchers = repository.findAll();

        assertThat(vouchers, not(empty()));
    }

    @Test
    @Order(Integer.MAX_VALUE)
    @DisplayName("bean이 destroy될 때 추가한 Voucher를 파일로 저장하는 것이 가능해야한다.")
    void testSaveJSONFile() {
        // given
        JSONFileVoucherRepository repository = getJSONFileRepository();
        Voucher generatedVoucher = generateVoucher();

        // when
        repository.save(generatedVoucher);
        repository.destroy();

        // then
        JSONFileVoucherRepository repositoryAfterDestroy = getJSONFileRepository();
        List<Voucher> vouchersAfterDestroy = repositoryAfterDestroy.findAll();

        assertThat(vouchersAfterDestroy, hasItem(samePropertyValuesAs(generatedVoucher)));
    }
}
