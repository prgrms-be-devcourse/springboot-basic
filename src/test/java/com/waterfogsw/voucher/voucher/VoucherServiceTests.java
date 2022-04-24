package com.waterfogsw.voucher.voucher;

import com.waterfogsw.voucher.voucher.domain.FixedAmountVoucher;
import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.exception.RepositoryException;
import com.waterfogsw.voucher.voucher.repository.VoucherRepository;
import com.waterfogsw.voucher.voucher.service.VoucherManageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class VoucherServiceTests {
    @Mock
    VoucherRepository voucherRepository;

    @InjectMocks
    VoucherManageService voucherService;

    @Nested
    @DisplayName("addVoucher 메소드는")
    class Describe_addVoucher {

        @Nested
        @Order(1)
        @DisplayName("repository 에서 NPE 가 발생하면")
        class Context_with_negative_fixedAmount {

            @Test
            @DisplayName("RepositoryException 예외를 발생시킨다")
            void it_throw_RepositoryException() {
                Voucher voucher = new FixedAmountVoucher(100);

                when(voucherRepository.save(any(Voucher.class))).thenThrow(NullPointerException.class);

                assertThrows(RepositoryException.class, () -> voucherService.addVoucher(voucher));
            }
        }

        @Nested
        @Order(2)
        @DisplayName("Voucher 가 정상적으로 저장되면")
        class Context_with_saved_success {

            @Test
            @DisplayName("저장한 Voucher 를 return 한다")
            void it_throw_IllegalArgumentException() throws RepositoryException {
                Voucher voucher = new FixedAmountVoucher(100);

                when(voucherRepository.save(any(Voucher.class))).thenReturn(voucher);

                var savedVoucher = voucherService.addVoucher(voucher);
                assertThat(savedVoucher.getType(), is(savedVoucher.getType()));
                assertThat(savedVoucher.getValue(), is(savedVoucher.getValue()));
            }
        }
    }
}
}
