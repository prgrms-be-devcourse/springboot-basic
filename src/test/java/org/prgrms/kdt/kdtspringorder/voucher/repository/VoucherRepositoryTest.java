package org.prgrms.kdt.kdtspringorder.voucher.repository;

import com.wix.mysql.EmbeddedMysql;
import com.wix.mysql.config.Charset;
import com.wix.mysql.config.MysqldConfig;
import org.junit.jupiter.api.*;
import org.prgrms.kdt.kdtspringorder.common.enums.VoucherType;
import org.prgrms.kdt.kdtspringorder.common.exception.VoucherNotFoundException;
import org.prgrms.kdt.kdtspringorder.voucher.domain.FixedAmountVoucher;
import org.prgrms.kdt.kdtspringorder.voucher.domain.Voucher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    class Describe_findById {

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
    @DisplayName("findByCustomerId 메서드는")
    class Describe_findByCustomerId {

        private Voucher newVoucher;
        private UUID newVoucherId = UUID.randomUUID();
        private UUID newCustomerId = UUID.randomUUID();
        private long newVoucherDiscount = 100;

        @BeforeEach
        void setUp() {
            newVoucher = new FixedAmountVoucher(newVoucherId, newCustomerId, newVoucherDiscount);
            repository.insert(newVoucher);
        }

        @Nested
        @DisplayName("CustomerId로 바우처를 조회한다면")
        class Context_voucher_id{
            @Test
            @DisplayName("고객에게 할당된 바우처 목록을 반환합니다.")
            void it_return_allocated_voucher_list() {
                final List<Voucher> allocatedVoucherList = repository.findByCustomerId(newCustomerId);
                assertThat(allocatedVoucherList, not(nullValue()));
                assertThat(allocatedVoucherList, hasSize(1));
            }

        }

    }

    @Nested
    @DisplayName("findByVoucherType 메서드는")
    class Describe_findByVoucherType {

        private Voucher newVoucher;
        private UUID newVoucherId = UUID.randomUUID();
        private UUID newCustomerId = UUID.randomUUID();
        private long newVoucherDiscount = 100;
        private VoucherType targetVoucherType;

        @BeforeEach
        void setUp() {
            newVoucher = new FixedAmountVoucher(newVoucherId, newCustomerId, newVoucherDiscount);
            repository.insert(newVoucher);
            targetVoucherType = newVoucher.getVoucherType();
        }

        @Nested
        @DisplayName("VoucherType으로 바우처를 조회한다면")
        class Context_voucherType{
            @Test
            @DisplayName("해당 Voucher 타입의 바우처목록을 반환합니다.")
            void it_return_voucher_list_by_voucher_type() {
                final List<Voucher> voucherListByType = repository.findByVoucherType(targetVoucherType);
                assertThat(voucherListByType, not(nullValue()));
                assertThat(voucherListByType, hasSize(1));
                voucherListByType.forEach(voucher -> {
                    assertThat(voucher, instanceOf(newVoucher.getClass()));
                });
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

    @Nested
    @DisplayName("delete 메소드는")
    class Describe_delete {

        @Nested
        @DisplayName("만약 바우처 목록에 없는 바우처를 삭제하는 경우")
        class Context_invalid_voucher_id{

            private UUID invalidVoucherId;

            @BeforeEach
            void setUp() {
                invalidVoucherId = UUID.randomUUID();
            }

            @Test
            @DisplayName("VoucherNotFound Exception을 던집니다.")
            void it_return_throw_voucher_not_found_exception() {

                assertThatThrownBy( () -> repository.delete(invalidVoucherId))
                    .isInstanceOf(VoucherNotFoundException.class);

            }

        }

        @Nested
        @DisplayName("만약 바우처 목록에 있는 바우처를 삭제하는 경우")
        class Context_valid_voucher_id{

            private Voucher newVoucher;
            private UUID newVoucherId = UUID.randomUUID();
            private long newVoucherDiscount = 100;

            @BeforeEach
            void setUp() {
                newVoucher = new FixedAmountVoucher(newVoucherId, newVoucherDiscount);
                repository.insert(newVoucher);
            }

            @Test
            @DisplayName("삭제한 컬럼 수를 반환합니다.")
            void it_return_deleted_column_count() {

                final int deletedColumnCount = repository.delete(newVoucherId);
                assertThat(deletedColumnCount, equalTo(1));
                final Optional<Voucher> foundVoucherOptional = repository.findById(newVoucherId);
                assertThat(foundVoucherOptional.isPresent(), is(false));

            }

        }

    }

    @Nested
    @DisplayName("updateDiscount 메소드는")
    class Describe_updateDiscount {

        @Nested
        @DisplayName("만약 바우처 목록에 없는 바우처의 할인금액 or 할인률을 수정하는 경우")
        class Context_invalid_id{

            private UUID invalidVoucherId;
            private long changeVoucherDiscount = 1000;

            @BeforeEach
            void setUp() {
                invalidVoucherId = UUID.randomUUID();
            }

            @Test
            @DisplayName("VoucherNotFound Exception을 던집니다.")
            void it_return_throw_voucher_not_found_exception() {

                assertThatThrownBy( () -> repository.updateDiscount(invalidVoucherId, changeVoucherDiscount))
                    .isInstanceOf(VoucherNotFoundException.class);

            }

        }

        @Nested
        @DisplayName("만약 바우처 목록에 있는 바우처의 할인금액 or 할인률을 수정하는 경우")
        class Context_valid_voucher_id{

            private Voucher newVoucher;
            private UUID newVoucherId = UUID.randomUUID();
            private long newVoucherDiscount = 100;
            private long changeVoucherDiscount = 1000;

            @BeforeEach
            void setUp() {
                newVoucher = new FixedAmountVoucher(newVoucherId, newVoucherDiscount);
                repository.insert(newVoucher);
            }

            @Test
            @DisplayName("할인금액 or 할인률을 수정한 후, 수정한 바우처의 UUID를 반환합니다.")
            void it_return_updated_voucher_id() {

                final UUID updatedVoucherId = repository.updateDiscount(newVoucher.getVoucherId(), changeVoucherDiscount);
                assertThat(updatedVoucherId, equalTo(newVoucher.getVoucherId()));

                final Optional<Voucher> foundVoucherOptional = repository.findById(updatedVoucherId);
                assertThat(foundVoucherOptional.isPresent(), is(true));

                final Voucher updatedVoucher = foundVoucherOptional.get();
                assertThat(updatedVoucher.getAmount(), equalTo(changeVoucherDiscount));

            }

        }

    }

    @Nested
    @DisplayName("updateCustomerId 메소드는")
    class Describe_updateCustomerId {

        @Nested
        @DisplayName("만약 바우처 목록에 없는 바우처의 고객ID를 수정하는 경우")
        class Context_invalid_id{

            private UUID invalidVoucherId;

            @BeforeEach
            void setUp() {
                invalidVoucherId = UUID.randomUUID();
            }

            @Test
            @DisplayName("VoucherNotFound Exception을 던집니다.")
            void it_return_throw_voucher_not_found_exception() {

                assertThatThrownBy( () -> repository.delete(invalidVoucherId))
                    .isInstanceOf(VoucherNotFoundException.class);

            }

        }

        @Nested
        @DisplayName("만약 바우처 목록에 있는 바우처의 고객ID를 수정하는 경우")
        class Context_valid_voucher_id{

            private Voucher newVoucher;
            private UUID newVoucherId = UUID.randomUUID();
            private UUID newCustomerId = UUID.randomUUID();
            private UUID changeCustomerId = UUID.randomUUID();
            private long newVoucherDiscount = 100;

            @BeforeEach
            void setUp() {
                newVoucher = new FixedAmountVoucher(newVoucherId, newCustomerId, newVoucherDiscount);
                repository.insert(newVoucher);
            }

            @Test
            @DisplayName("바우처가 할당된 고객 ID를 수정한 후, 수정한 바우처의 UUID를 반환합니다.")
            void it_return_updated_voucher_id() {

                final UUID updatedVoucherId = repository.updateCustomerId(newVoucher.getVoucherId(), changeCustomerId);
                final Optional<Voucher> foundVoucherOptional = repository.findById(updatedVoucherId);
                assertThat(foundVoucherOptional.isPresent(), is(true));

                final Voucher updatedVoucher = foundVoucherOptional.get();
                assertThat(updatedVoucher.getCustomerId(), equalTo(changeCustomerId));

            }

        }

    }


}
