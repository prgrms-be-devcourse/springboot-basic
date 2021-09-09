package org.prgrms.kdt.kdtspringorder.voucher.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.kdtspringorder.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.kdtspringorder.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasSize;

import static com.wix.mysql.EmbeddedMysql.anEmbeddedMysql;
import static com.wix.mysql.ScriptResolver.classPathScript;
import static com.wix.mysql.distribution.Version.v8_0_11;

@SpringBootTest
@Transactional
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DisplayName("VoucherRepository 단위 테스트")
class VoucherRepositoryTest {

    @Autowired
    private VoucherRepository repository;

    EmbeddedMysql embeddedMysql;

    @BeforeAll
    void setUp(){

        MysqldConfig config = MysqldConfig.aMysqldConfig(v8_0_11)
            .withCharset(Charset.UTF8)
            .withPort(2215)
//            .withUser("test", "1234")
            .withTimeZone("Asia/Seoul")
            .build();

        embeddedMysql = anEmbeddedMysql(config)
            .addSchema("test-order-mgmt", classPathScript("schema.sql"))
            .start();
    }

    @AfterAll
    void cleanup() {
        embeddedMysql.stop();
    }

    @Nested
    @DisplayName("findAll 메서드는")
    class Describe_findAll {

        @Nested
        @DisplayName("만약 목록이 비어있으면")
        class Context_empty{

            @Test
            @DisplayName("빈 바우처 목록을 반환합니다.")
            void It_return_empty_list() {
                final List<Voucher> vouchers = repository.findAll();
                assertThat(vouchers, notNullValue());
                assertThat(vouchers, hasSize(0));
            }

        }

        @Nested
        @Rollback
        @DisplayName("만약 목록이 비어있지 않으면")
        class Context_not_empty{

            private Voucher newVoucher;
            private UUID newVoucherId = UUID.randomUUID();
            private long newVoucherDiscount = 100;

            @BeforeEach
            void setUp() {
                newVoucher = new FixedAmountVoucher(newVoucherId, newVoucherDiscount);
                repository.insert(newVoucher);
            }

            @Test
            @DisplayName("비어있지 않은 바우처 목록을 반환합니다.")
            void It_return_not_empty_list() {

                final List<Voucher> vouchers = repository.findAll();
                assertThat(vouchers, not(hasSize(0)));

            }

        }

    }

    @Nested
    @DisplayName("findById 메서드는")
    class Describe_getVoucher {

        private Voucher newVoucher;
        private UUID newVoucherId = UUID.randomUUID();
        private long newVoucherDiscount = 100;

        @BeforeEach
        void setUp() {
            newVoucher = new FixedAmountVoucher(newVoucherId, newVoucherDiscount);
            repository.insert(newVoucher);
        }

        @Nested
        @DisplayName("VoucherId로 바우처를 조회한다면")
        class Context_voucher_id{

            @Test
            @DisplayName("Optional<Voucher> 객체를 반환합니다.")
            void it_return_voucher_optional() {
                final Optional<Voucher> foundVoucherOptional = repository.findById(newVoucherId);
                assertThat(foundVoucherOptional, not(nullValue()));
                assertThat(foundVoucherOptional, instanceOf(Optional.class));
            }

        }

    }

    @Nested
    @DisplayName("insert 메서드는")
    class Describe_insert {

        @Nested
        @DisplayName("등록할 Voucher를 인자로 넘겨서 바우처를 등록하면")
        class Context_voucher_id{

            private Voucher newVoucher;
            private UUID newVoucherId = UUID.randomUUID();
            private long newVoucherDiscount = 100;

            @BeforeEach
            void setUp() {
                newVoucher = new FixedAmountVoucher(newVoucherId, newVoucherDiscount);
            }

            @Test
            @DisplayName("등록한 VoucherID를 반환합니다.")
            void it_return_voucher_optional() {
                final UUID createdVoucherId = repository.insert(newVoucher);
                assertThat(createdVoucherId, equalTo(newVoucherId));
            }

        }

    }


}
