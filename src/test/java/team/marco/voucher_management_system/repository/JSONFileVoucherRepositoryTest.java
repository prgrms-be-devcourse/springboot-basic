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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import team.marco.voucher_management_system.configuration.TestPropertyConfiguration;
import team.marco.voucher_management_system.model.Voucher;
import team.marco.voucher_management_system.properties.FilePathProperties;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@SpringJUnitConfig(TestPropertyConfiguration.class)
class JSONFileVoucherRepositoryTest extends VoucherRepositoryTest {
    private static final String EMPTY_DATA = "[]";
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

    @BeforeEach
    void cleanup() {
        writeJSONFile(EMPTY_DATA);
    }

    @Test
    @DisplayName("레포지토리 생성 시 이전에 저장된 데이터를 로드해야한다.")
    void testLoad() {
        // given
        writeJSONFile(SETUP_DATA);

        // when
        JSONFileVoucherRepository repository = getJSONFileRepository();

        // then
        List<Voucher> vouchers = repository.findAll();

        assertThat(vouchers, not(empty()));
    }

    @Test
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

    private void writeJSONFile(String contents) {
        try (FileWriter fileWriter = new FileWriter(filePathProperties.voucherData())) {
            fileWriter.write(contents);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }
}
