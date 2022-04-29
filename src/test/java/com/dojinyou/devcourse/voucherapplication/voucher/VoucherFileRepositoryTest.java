package com.dojinyou.devcourse.voucherapplication.voucher;

import com.dojinyou.devcourse.voucherapplication.VoucherApplication;
import com.dojinyou.devcourse.voucherapplication.utils.CsvFileUtils;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.Voucher;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherAmount;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherMapper;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherType;
import com.dojinyou.devcourse.voucherapplication.voucher.dto.VoucherRequest;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = VoucherApplication.class)
class VoucherFileRepositoryTest {

    private static final String ERROR_MESSAGE_ABOUT_REFLEXTION = "reflextion 과정에서 에러가 발생하였습니다.\n";
    private final int idIndex = 0;
    private final int lastLineNumber = -1;
    private final Path TEST_FILE_PATH;

    @Autowired()
    VoucherFileRepository voucherRepository;

    VoucherFileRepositoryTest(@Value("${test-db-file-path}") String testFilePath) {
        TEST_FILE_PATH = Paths.get(testFilePath+"/Voucher.csv");
    }

    @BeforeAll
    void setUp() {
        // test File(DB)로 Path 변경 필요
        try {
            Field filePathFiled = VoucherFileRepository.class.getDeclaredField("FILE_PATH");
            filePathFiled.setAccessible(true);
            filePathFiled.set(voucherRepository, TEST_FILE_PATH);
        } catch (NoSuchFieldException|IllegalAccessException e) {
            fail(ERROR_MESSAGE_ABOUT_REFLEXTION + e.getMessage());
        }
    }

    @Nested
    @DisplayName("Create method에 관하여")
    class Describe_create_method {
        @Nested
        @DisplayName("잘못된 domain이 들어온다면,")
        class Context_Illegal_VoucherCreateDTo {

            @ParameterizedTest
            @NullSource
            @DisplayName("예외를 발생시킨다.")
            void it_throws_Exception(Voucher voucher) {
                // given

                // when
                Throwable thrown = catchThrowable(() -> voucherRepository.create(voucher));

                // then
                assertThat(thrown).isNotNull();
                assertThat(thrown).isInstanceOf(IllegalArgumentException.class);
            }
        }

        @Nested
        @DisplayName("정상적인 domain가 들어온다면,")
        class Context_Correct_VoucherCreateDto {

            @ParameterizedTest
            @EnumSource(VoucherType.class)
            @DisplayName("id를 가진 Voucher를 return한다.")
            void it_throws_Exception(VoucherType voucherType) {
                // given
                int amount = 50;
                VoucherAmount voucherAmount = VoucherAmount.of(voucherType, amount);
                Voucher voucher = VoucherMapper.requestDtoToDomain(new VoucherRequest(voucherType, voucherAmount));

                int lastId = 0;
                if(CsvFileUtils.read(TEST_FILE_PATH).size() != 0) {
                    String[] lastData = CsvFileUtils.readLine(TEST_FILE_PATH, lastLineNumber);
                    lastId = Integer.parseInt(lastData[idIndex]);
                }

                // when
                Voucher savedVoucher = voucherRepository.create(voucher);

                // then
                assertThat(savedVoucher).isNotNull();
                assertThat(savedVoucher.getVoucherId()).isEqualTo(lastId + 1);
                assertThat(savedVoucher.getVoucherType()).isEqualTo(voucherType);
                assertThat(savedVoucher.getVoucherAmount()).isEqualTo(voucherAmount);
            }
        }
    }


    @Nested
    @DisplayName("findAll method에 관하여")
    class Describe_findAll_method {
        @Nested
        @DisplayName("함수가 호출 되었을 때,")
        class Context_MethodCall {

            @Test
            @DisplayName("VoucherList type을 return 한다")
            void it_Return_VoucherList_Object() {
                // given
                List<String[]> readData = CsvFileUtils.read(TEST_FILE_PATH);

                // when
                List<Voucher> vouchers = voucherRepository.findAll();

                // then
                assertThat(vouchers).isNotNull();
                assertThat(vouchers.size()).isEqualTo(readData.size());
            }
        }
    }
}