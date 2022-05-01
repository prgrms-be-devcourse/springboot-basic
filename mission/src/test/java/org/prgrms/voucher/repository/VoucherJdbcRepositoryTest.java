package org.prgrms.voucher.repository;


import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.ScriptResolver;
import com.wix.mysql.config.Charset;
import com.zaxxer.hikari.HikariDataSource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.prgrms.voucher.models.FixedAmountVoucher;
import org.prgrms.voucher.models.Voucher;
import org.prgrms.voucher.models.VoucherType;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.config.MysqldConfig.aMysqldConfig;
import static com.wix.mysql.distribution.Version.v8_0_11;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class VoucherJdbcRepositoryTest {

    static EmbeddedMysql embeddedMysql;

    @BeforeAll
    static void setup() {
        var config= aMysqldConfig(v8_0_11)
                .withCharset(Charset.UTF8)
                .withPort(2215)
                .withUser("test", "test1234!")
                .build();
        embeddedMysql = anEmbeddedMysql(config)
                .addSchema("voucher-program", ScriptResolver.classPathScript("schema.sql"))
                .start();
    }

    @AfterAll
    static void cleanup() {
        embeddedMysql.stop();
    }

    private final DataSource dataSource = DataSourceBuilder.create()
            .url("jdbc:mysql://localhost:2215/voucher-program")
            .username("test")
            .password("test1234!")
            .type(HikariDataSource.class)
            .build();

    private final VoucherJdbcRepository voucherRepository = new VoucherJdbcRepository(new NamedParameterJdbcTemplate(dataSource));

    @Nested
    @Order(1)
    @DisplayName("Repository save 메서드는")
    class DescribeCreate {

        @Nested
        @Order(1)
        @DisplayName("save 기능을 테스트 할 때 바우처 객체를 null로 받으면")
        class ContextReceiveFixedVoucherTypeAndWrongValue {

            Voucher voucher = null;

            @Test
            @DisplayName("잘못된 인자 예외를 던진다.")
            void itIllegalArgumentExceptionThrow() {

                Assertions.assertThatThrownBy(() -> voucherRepository.save(voucher))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("Voucher is null");
            }
        }

        @Nested
        @Order(2)
        @DisplayName("save 기능을 테스트 할 때 알 수 없는 ID의 바우처 객체를 인자로 받으면")
        class ContextReceiveWrongVoucher {

            Voucher wrongVoucher = new FixedAmountVoucher(2L, 120, VoucherType.FIXED_AMOUNT, LocalDateTime.now(), LocalDateTime.now());

            @Test
            @DisplayName("잘못된 바우처 예외를 반환한다.")
            void itReturnWrongVoucherException() {

                Assertions.assertThatThrownBy(() -> voucherRepository.save(wrongVoucher))
                        .isInstanceOf(IllegalArgumentException.class)
                        .hasMessage("Wrong Voucher..");
            }
        }

        @Nested
        @Order(3)
        @DisplayName("save 기능을 테스트 할 때 새로 생성된 바우처(id = null) 객체를 인자로 받으면")
        class ContextReceiveNullVoucherType {

            Voucher voucher = new FixedAmountVoucher( 100, VoucherType.FIXED_AMOUNT);

            @Test
            @DisplayName("데이터 베이스 저장소에 저장하고 저장한 바우처를 반환한다.")
            void itVoucherSaveAndReturn() {

                Voucher voucherCheck = voucherRepository.save(voucher);

                Assertions.assertThat(voucherCheck.getDiscountValue()).isEqualTo(voucher.getDiscountValue());
                Assertions.assertThat(voucherCheck.getVoucherType()).isEqualTo(voucher.getVoucherType());
            }
        }

        @Nested
        @Order(4)
        @DisplayName("save 기능을 테스트 할 때 이미 있는 ID의 바우처 객체를 인자로 받으면")
        class ContextReceiveDuplicateId {

            @Test
            @DisplayName("해당 바우처를 업데이트 한다.")
            void itUpdateThisVoucher() {

            }
        }
    }

    @Nested
    @Order(2)
    @DisplayName("Repository findAll 메서드는")
    class DescribeFindAllMethod {

        @Nested
        @DisplayName("호출이 되면")
        class ContextCallThis {

            @Test
            @DisplayName("저장소의 바우처 정보를 리스트로 반환한다.")
            void itReturnVoucherList() {

                List<Voucher> list = voucherRepository.findAll();

                Assertions.assertThat(list.isEmpty()).isFalse();
                Assertions.assertThat(list.get(0).getVoucherId()).isEqualTo(1);
            }
        }
    }
}