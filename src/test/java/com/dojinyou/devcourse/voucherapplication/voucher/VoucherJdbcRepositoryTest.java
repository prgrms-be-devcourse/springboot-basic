package com.dojinyou.devcourse.voucherapplication.voucher;

import com.dojinyou.devcourse.voucherapplication.voucher.domain.Voucher;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherAmount;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherMapper;
import com.dojinyou.devcourse.voucherapplication.voucher.domain.VoucherType;
import com.dojinyou.devcourse.voucherapplication.voucher.dto.VoucherRequest;
import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import com.wix.mysql.distribution.Version;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;


@EnableAutoConfiguration
@SpringBootTest(classes = {VoucherJdbcRepository.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestClassOrder(ClassOrderer.OrderAnnotation.class)
class VoucherJdbcRepositoryTest {

    @Autowired
    VoucherJdbcRepository voucherRepository;

    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp() {
        MysqldConfig mysqlConfig = aMysqldConfig(Version.v5_7_10)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "testmysql")
                .withTimeZone("Asia/Seoul")
                .build();

        embeddedMysql = anEmbeddedMysql(mysqlConfig)
                .addSchema("spring-boot-mission", classPathScript("schema-test.sql"))
                .start();
    }

    @AfterAll
    void cleanUp() {
        embeddedMysql.stop();
    }

    @Nested
    @DisplayName("Create method에 관하여")
    @Order(1)
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
            void it_Return_Voucher(VoucherType voucherType) {
                // given
                int amount = 50;
                VoucherAmount voucherAmount = VoucherAmount.of(voucherType, amount);
                Voucher voucher = VoucherMapper.requestDtoToDomain(new VoucherRequest(voucherType, voucherAmount));

                // when
                Voucher savedVoucher = voucherRepository.create(voucher);

                // then
                assertThat(savedVoucher).isNotNull();
                assertThat(savedVoucher.getVoucherType()).isEqualTo(voucherType);
                assertThat(savedVoucher.getVoucherAmount()).isEqualTo(voucherAmount);
            }
        }
    }


    @Nested
    @Order(2)
    @DisplayName("findAll method에 관하여")
    class Describe_findAll_method {
        @Nested
        @DisplayName("함수가 호출 되었을 때,")
        class Context_MethodCall {

            @Test
            @DisplayName("List<Voucher> type을 return 한다")
            void it_Return_VoucherList_Object() {
                // given

                // when
                List<Voucher> vouchers = voucherRepository.findAll();

                // then
                assertThat(vouchers).isNotNull();
                assertThat(vouchers.size()).isEqualTo(2);
            }
        }
    }
}