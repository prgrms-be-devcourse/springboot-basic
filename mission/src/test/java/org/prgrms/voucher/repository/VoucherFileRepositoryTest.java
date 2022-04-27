package org.prgrms.voucher.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.prgrms.voucher.models.FixedAmountVoucher;
import org.prgrms.voucher.models.Voucher;
import org.prgrms.voucher.models.VoucherType;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

@TestClassOrder(ClassOrderer.OrderAnnotation.class)
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class VoucherFileRepositoryTest {

    String filePath = "src/main/resources/voucherFileRepository.txt";
    private final VoucherRepository voucherRepository = new VoucherFileRepository(filePath);

    @Nested
    @Order(1)
    @DisplayName("Repository save 메서드는")
    class DescribeCreate {

        @Nested
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
        @DisplayName("save 기능을 테스트 할 때 바우처 객체를 인자로 받으면")
        class ContextReceiveNullVoucherType {

            Voucher voucher = new FixedAmountVoucher(1L, 100, VoucherType.FIXED_AMOUNT);

            @Test
            @DisplayName("파일 저장소에 저장하고 저장한 바우처를 반환한다.")
            void itVoucherSaveAndReturn() {

                Voucher voucherCheck = voucherRepository.save(voucher);

                Assertions.assertThat(voucherCheck).isEqualTo(voucher);
            }
        }
    }

    @Nested
    @Order(2)
    @DisplayName("Repository findAll 메서드는")
    class DescribeFindAll {

        @Nested
        @DisplayName("호출이 되면")
        class ContextCallThis {

            @Test
            @DisplayName("저장소의 바우처 정보를 리스트로 반환한다.")
            void itReturnVoucherList() {

                List<Voucher> list = voucherRepository.findAll();

                Assertions.assertThat(list.isEmpty()).isFalse();
            }
        }
    }
}

