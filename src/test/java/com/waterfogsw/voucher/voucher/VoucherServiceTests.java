package com.waterfogsw.voucher.voucher;

import com.waterfogsw.voucher.voucher.domain.FixedAmountVoucher;
import com.waterfogsw.voucher.voucher.domain.Voucher;
import com.waterfogsw.voucher.voucher.exception.ResourceNotFound;
import com.waterfogsw.voucher.voucher.repository.VoucherRepository;
import com.waterfogsw.voucher.voucher.service.VoucherManageService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
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
        @DisplayName("인자로 전달받은 Voucher 가 null 이면")
        class Context_with_negative_fixedAmount {

            @Test
            @DisplayName("IllegalArgumentException 예외를 발생시킨다")
            void it_throw_RepositoryException() {
                assertThrows(IllegalArgumentException.class, () -> voucherService.saveVoucher(null));
            }
        }

        @Nested
        @DisplayName("Voucher 가 정상적으로 저장되면")
        class Context_with_saved_success {

            @Test
            @DisplayName("저장한 Voucher 를 return 한다")
            void it_throw_IllegalArgumentException() {
                Voucher voucher = new FixedAmountVoucher(100);

                when(voucherRepository.save(any(Voucher.class))).thenReturn(voucher);

                var savedVoucher = voucherService.saveVoucher(voucher);
                assertThat(savedVoucher.getType(), is(savedVoucher.getType()));
                assertThat(savedVoucher.getValue(), is(savedVoucher.getValue()));
            }
        }
    }

    @Nested
    @DisplayName("findAllVoucher 메소드는")
    class Describe_findAllVoucher {

        @Nested
        @DisplayName("호출되면")
        class Context_with_negative_fixedAmount {

            @Test
            @DisplayName("저장한 Voucher 리스트를 반환 한다")
            void it_throw_IllegalArgumentException() {
                List<Voucher> voucherList = new ArrayList<>();
                Voucher voucher1 = new FixedAmountVoucher(100);
                Voucher voucher2 = new FixedAmountVoucher(100);

                voucherList.add(voucher1);
                voucherList.add(voucher2);

                when(voucherRepository.findAll()).thenReturn(voucherList);

                var voucherLists = voucherService.findAllVoucher();

                assertThat(voucherLists.size(), is(2));
            }
        }
    }

    @Nested
    @DisplayName("findById 메소드는")
    class Describe_findById {

        @Nested
        @DisplayName("id 값이 존재하면")
        class Context_with_exist_id {

            @Test
            @DisplayName("해당 id의 바우처를 리턴한다")
            void it_throw_IllegalArgumentException() {
                final var voucher = new FixedAmountVoucher(1L, 1000, LocalDateTime.now(), LocalDateTime.now());
                when(voucherRepository.findById(anyLong())).thenReturn(Optional.of(voucher));

                final var findVoucher = voucherService.findById(1L);
                assertThat(findVoucher, samePropertyValuesAs(voucher));
            }
        }

        @Nested
        @DisplayName("id 값이 존재하지 않으면")
        class Context_with_not_exist_id {

            @Test
            @DisplayName("optional empty 를 리턴한다")
            void it_throw_IllegalArgumentException() {
                when(voucherRepository.findById(anyLong())).thenReturn(Optional.empty());

                assertThrows(ResourceNotFound.class, () -> voucherService.findById(1L));
            }
        }
    }
}
