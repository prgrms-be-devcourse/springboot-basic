package org.prgms.voucherProgram.service;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.BDDMockito.*;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.prgms.voucherProgram.domain.customer.Customer;
import org.prgms.voucherProgram.domain.voucher.FixedAmountVoucher;
import org.prgms.voucherProgram.domain.voucher.PercentDiscountVoucher;
import org.prgms.voucherProgram.domain.voucher.Voucher;
import org.prgms.voucherProgram.dto.VoucherRequest;
import org.prgms.voucherProgram.dto.WalletRequest;
import org.prgms.voucherProgram.exception.AlreadyAssignException;
import org.prgms.voucherProgram.exception.CustomerIsNotExistsException;
import org.prgms.voucherProgram.exception.NotFoundVoucherException;
import org.prgms.voucherProgram.exception.VoucherIsNotExistsException;
import org.prgms.voucherProgram.repository.customer.CustomerRepository;
import org.prgms.voucherProgram.repository.voucher.VoucherRepository;

@ExtendWith(MockitoExtension.class)
class VoucherServiceTest {

    @Mock
    VoucherRepository voucherRepository;

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    VoucherService voucherService;

    private VoucherRequest voucherRequest() {
        return new VoucherRequest(1, 10L);
    }

    private WalletRequest walletRequest() {
        return new WalletRequest("hwan@gmail.com", UUID.randomUUID());
    }

    private FixedAmountVoucher voucher() {
        return new FixedAmountVoucher(UUID.randomUUID(), 10L);
    }

    private List<Voucher> vouchers() {
        return List.of(new FixedAmountVoucher(UUID.randomUUID(), 10L),
            new PercentDiscountVoucher(UUID.randomUUID(), UUID.randomUUID(), 20L));
    }

    private Customer customer() {
        return new Customer(UUID.randomUUID(), "hwan", "hwan@gmail.com", LocalDateTime.now());
    }

    @Nested
    @DisplayName("create 메서드는")
    class Describe_create {

        @Nested
        @DisplayName("바우처 정보가 주어지면")
        class Context_with_voucher {
            final VoucherRequest voucherRequest = voucherRequest();
            final Voucher voucher = voucher();

            @BeforeEach
            void prepare() {
                given(voucherRepository.save(any(Voucher.class))).willReturn(voucher);
            }

            @Test
            @DisplayName("바우처를 만들어 저장하고 저장된 바우처를 리턴한다.")
            void it_makes_voucher_and_returns_saved_voucher() {
                Voucher newVoucher = voucherService.create(voucherRequest);

                assertThat(newVoucher).usingRecursiveComparison()
                    .isEqualTo(voucher);
                then(voucherRepository).should(times(1)).save(any(Voucher.class));
            }
        }
    }

    @Nested
    @DisplayName("findAll 메서드는")
    class Describe_findAll {
        @Nested
        @DisplayName("저장된 바우처들이 있다면")
        class Context_with_saved_vouchers {
            final List<Voucher> vouchers = vouchers();

            @BeforeEach
            void prepare() {
                given(voucherRepository.findAll()).willReturn(vouchers);
            }

            @Test
            @DisplayName("모든 바우처를 리턴한다.")
            void it_returns_all_voucher() {
                List<Voucher> findVouchers = voucherService.findAllVoucher();

                assertThat(findVouchers).hasSize(2)
                    .usingRecursiveFieldByFieldElementComparatorIgnoringFields()
                    .containsAll(vouchers);
                then(voucherRepository).should(times(1)).findAll();
            }
        }
    }

    @Nested
    @DisplayName("update 메서드는")
    class Describe_update {

        @Nested
        @DisplayName("해당 아이디를 가진 바우처가 있다면")
        class Context_with_saved_voucher_id {
            final VoucherRequest voucherRequest = voucherRequest();
            final Voucher voucher = voucher();
            final UUID voucherId = voucher.getVoucherId();
            final Voucher updateVoucher = new FixedAmountVoucher(voucherId, voucherRequest.getDiscountValue());

            @BeforeEach
            void prepare() {
                given(voucherRepository.findById(voucherId)).willReturn(Optional.of(voucher));
                given(voucherRepository.update(any(Voucher.class))).willReturn(updateVoucher);
            }

            @Test
            @DisplayName("바우처를 수정한다.")
            void it_update_voucher() {
                Voucher updated = voucherService.update(voucherId, voucherRequest);

                assertThat(updateVoucher).usingRecursiveComparison()
                    .isEqualTo(updated);
                then(voucherRepository).should(times(1)).findById(any(UUID.class));
                then(voucherRepository).should(times(1)).update(any(Voucher.class));
            }
        }

        @Nested
        @DisplayName("해당 아이디를 가진 바우처가 없다면")
        class Context_with_not_saved_voucher_id {
            final VoucherRequest voucherRequest = voucherRequest();
            UUID voucherId = UUID.randomUUID();

            @BeforeEach
            void prepare() {
                given(voucherRepository.findById(voucherId)).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("예외를 발생한다.")
            void it_returns_exception() {
                assertThatThrownBy(() -> voucherService.update(voucherId, voucherRequest))
                    .isInstanceOf(VoucherIsNotExistsException.class)
                    .hasMessage("[ERROR] 해당 아이디로 저장된 바우처가 없습니다.");
                then(voucherRepository).should(times(1)).findById(any(UUID.class));
                then(voucherRepository).should(times(0)).update(any(Voucher.class));
            }
        }
    }

    @Nested
    @DisplayName("delete 메서드는")
    class Describe_delete {

        @Nested
        @DisplayName("해당 아이디를 가진 바우처가 있다면")
        class Context_with_saved_voucher_id {
            final UUID voucherId = UUID.randomUUID();
            final Voucher voucher = new FixedAmountVoucher(voucherId, 10L);

            @BeforeEach
            void prepare() {
                given(voucherRepository.findById(voucherId)).willReturn(Optional.of(voucher));
            }

            @Test
            @DisplayName("바우처를 삭제한다.")
            void it_delete_voucher() {
                voucherService.delete(voucherId);

                then(voucherRepository).should(times(1)).findById(any(UUID.class));
                then(voucherRepository).should(times(1)).deleteById(any(UUID.class));
            }
        }

        @Nested
        @DisplayName("해당 아이디를 가진 바우처가 없다면")
        class Context_with_not_saved_voucher_id {
            final UUID voucherId = UUID.randomUUID();

            @BeforeEach
            void prepare() {
                given(voucherRepository.findById(voucherId)).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("예외를 발생한다.")
            void it_throws_exception() {
                assertThatThrownBy(() -> voucherService.delete(voucherId))
                    .isInstanceOf(VoucherIsNotExistsException.class)
                    .hasMessage("[ERROR] 해당 아이디로 저장된 바우처가 없습니다.");
                then(voucherRepository).should(times(1)).findById(any(UUID.class));
                then(voucherRepository).should(times(0)).deleteById(any(UUID.class));
            }
        }
    }

    @Nested
    @DisplayName("assignVoucher 메서드는")
    class Describe_assignVoucher {
        @Nested
        @DisplayName("정상적인 고객과 바우처가 주어진다면")
        class Context_with_customer_and_voucher {
            final Voucher voucher = voucher();
            final Customer customer = customer();
            final WalletRequest walletRequest = walletRequest();

            @BeforeEach
            void prepare() {
                given(customerRepository.findByEmail(any(String.class))).willReturn(Optional.of(customer));
                given(voucherRepository.findById(any(UUID.class))).willReturn(Optional.of(voucher));
                given(voucherRepository.assignCustomer(any(Voucher.class))).willReturn(
                    new FixedAmountVoucher(voucher.getVoucherId(), customer.getCustomerId(),
                        voucher.getDiscountValue()));
            }

            @Test
            @DisplayName("고객에게 바우처를 할당하고 할당된 바우처를 리턴한다.")
            void it_assign_voucher_to_customer_and_returns_assigned_voucher() {
                Voucher assignVoucher = voucherService.assignVoucher(walletRequest);

                assertThat(assignVoucher).extracting("customerId")
                    .isEqualTo(customer.getCustomerId());
                then(customerRepository).should(times(1)).findByEmail(any(String.class));
                then(voucherRepository).should(times(1)).findById(any(UUID.class));
                then(voucherRepository).should(times(1)).assignCustomer(any(Voucher.class));
            }
        }

        @Nested
        @DisplayName("주어진 고객 이메일이 존재하지 않는다면")
        class Context_with_not_saved_customer {
            final Voucher voucher = voucher();
            final WalletRequest walletRequest = walletRequest();

            @BeforeEach
            void prepare() {
                given(voucherRepository.findById(any(UUID.class))).willReturn(Optional.of(voucher));
                given(customerRepository.findByEmail(any(String.class))).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("예외를 발생한다.")
            void it_returns_exception() {
                assertThatThrownBy(() -> voucherService.assignVoucher(walletRequest))
                    .isInstanceOf(CustomerIsNotExistsException.class)
                    .hasMessage("[ERROR] 해당 이메일로 저장된 고객이 없습니다.");
            }
        }

        @Nested
        @DisplayName("주어진 바우처 아이디가 존재하지 않는다면")
        class Context_with_not_saved_voucher {
            final WalletRequest walletRequest = walletRequest();

            @BeforeEach
            void prepare() {
                given(voucherRepository.findById(any(UUID.class))).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("예외를 발생한다.")
            void it_throws_excpetion() {
                assertThatThrownBy(() -> voucherService.assignVoucher(walletRequest))
                    .isInstanceOf(VoucherIsNotExistsException.class)
                    .hasMessage("[ERROR] 해당 아이디로 저장된 바우처가 없습니다.");
            }
        }

        @Nested
        @DisplayName("이미 할당된 바우처라면")
        class Context_with_already_assign_voucher {
            Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), UUID.randomUUID(), 10L);
            WalletRequest walletRequest = walletRequest();

            @BeforeEach
            void prepare() {
                given(voucherRepository.findById(any(UUID.class))).willReturn(Optional.of(voucher));
            }

            @Test
            @DisplayName("예외를 발생한다.")
            void it_returns_exception() {
                assertThatThrownBy(() -> voucherService.assignVoucher(walletRequest))
                    .isInstanceOf(AlreadyAssignException.class)
                    .hasMessage("[ERROR] 해당 바우처는 이미 할당되었습니다.");
            }
        }

    }

    @Nested
    @DisplayName("findAssginVouchers 메서드는")
    class Describe_findAssginVouchers {

        @Nested
        @DisplayName("고객에게 할당된 바우처들이 있다면")
        class Context_with_customer_has_vouchers {
            final Customer customer = customer();
            final List<Voucher> vouchers = vouchers();

            @BeforeEach
            void prepare() {
                given(customerRepository.findByEmail(any(String.class))).willReturn(Optional.of(customer));
                given(voucherRepository.findByCustomerEmail(any(String.class))).willReturn(vouchers);
            }

            @Test
            @DisplayName("고객에게 할당된 바우처들을 반환한다.")
            void it_returns_assigned_vouchers() {
                List<Voucher> vouchers = voucherService.findAssignVouchers(customer.getEmail());

                assertThat(vouchers).hasSize(2);
                then(customerRepository).should(times(1)).findByEmail(any(String.class));
                then(voucherRepository).should(times(1)).findByCustomerEmail(any(String.class));
            }
        }

        @Nested
        @DisplayName("해당 이메일로 저장된 고객이 없다면")
        class Context_with_not_saved_email_customer {
            String email = "hwan@gmail.com";

            @BeforeEach
            void prepare() {
                given(customerRepository.findByEmail(any(String.class))).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("예외를 발생한다.")
            void it_throws_excpetion() {
                assertThatThrownBy(() -> voucherService.findAssignVouchers(email))
                    .isInstanceOf(CustomerIsNotExistsException.class)
                    .hasMessage("[ERROR] 해당 이메일로 저장된 고객이 없습니다.");
            }
        }
    }

    @Nested
    @DisplayName("findCustomer 메서드는")
    class Describe_findCustomer {
        @Nested
        @DisplayName("특정 바우처를 가진 고객이 있다면")
        class Context_with_customer_has_voucher {
            final Customer customer = customer();
            final Voucher voucher = new FixedAmountVoucher(UUID.randomUUID(), customer.getCustomerId(), 10L);

            @BeforeEach
            void prepare() {
                given(voucherRepository.findById(any(UUID.class))).willReturn(Optional.of(voucher));
                given(customerRepository.findByVoucherId(any(UUID.class))).willReturn(Optional.of(customer));
            }

            @Test
            @DisplayName("해당 고객을 찾아 고객을 리턴한다.")
            void it_find_customer_and_returns_customer() {
                Customer findCustomer = voucherService.findCustomer(voucher.getVoucherId());

                assertThat(findCustomer).extracting("customerId").isEqualTo(customer.getCustomerId());
                then(voucherRepository).should(times(1)).findById(any(UUID.class));
                then(customerRepository).should(times(1)).findByVoucherId(any(UUID.class));
            }
        }

        @Nested
        @DisplayName("특정 바우처가 존재하지 않는다면")
        class Context_with_not_saved_voucher {
            final UUID voucherId = UUID.randomUUID();

            @BeforeEach
            void prepare() {
                given(voucherRepository.findById(any(UUID.class))).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("예외를 발생한다.")
            void it_throws_exception() {
                assertThatThrownBy(() -> voucherService.findCustomer(voucherId))
                    .isInstanceOf(VoucherIsNotExistsException.class)
                    .hasMessage("[ERROR] 해당 아이디로 저장된 바우처가 없습니다.");
            }
        }

        @Nested
        @DisplayName("특정 바우처가 아직 할당전이라면")
        class Context_with_not_assign_voucher {
            final Voucher voucher = voucher();

            @BeforeEach
            void prepare() {
                given(voucherRepository.findById(any(UUID.class))).willReturn(Optional.of(voucher));
            }

            @Test
            @DisplayName("예외를 발생한다.")
            void it_throws_exception() {
                assertThatThrownBy(() -> voucherService.findCustomer(voucher.getVoucherId()))
                    .isInstanceOf(CustomerIsNotExistsException.class)
                    .hasMessage("[ERROR] 해당 바우처는 아직 할당전 입니다.");
            }
        }
    }

    @Nested
    @DisplayName("deleteAssignVoucher 메서드는")
    class Describe_deleteAssignVoucher {
        @Nested
        @DisplayName("정상적인 바우처와 고객이 주어진다면")
        class Context_with_voucher_and_customer {
            final Voucher voucher = voucher();
            final Customer customer = customer();
            final WalletRequest walletRequest = new WalletRequest(customer.getEmail(), voucher.getVoucherId());

            @BeforeEach
            void prepare() {
                given(customerRepository.findByEmail(any(String.class))).willReturn(Optional.of(customer));
                given(voucherRepository.findByCustomerId(any(UUID.class))).willReturn(List.of(voucher));
            }

            @Test
            @DisplayName("고객이 보유한 바우처를 삭제한다.")
            void it_delete_assign_voucher() {
                voucherService.deleteAssignVoucher(walletRequest);

                then(customerRepository).should(times(1)).findByEmail(any(String.class));
                then(voucherRepository).should(times(1)).findByCustomerId(any(UUID.class));
                then(voucherRepository).should(times(1)).deleteById(any(UUID.class));
            }
        }

        @Nested
        @DisplayName("존재하지 않는 고객이라면")
        class Context_with_not_saved_customer {
            final WalletRequest walletRequest = walletRequest();

            @BeforeEach
            void prepare() {
                given(customerRepository.findByEmail(any(String.class))).willReturn(Optional.empty());
            }

            @Test
            @DisplayName("예외를 발생한다.")
            void it_throws_exception() {
                assertThatThrownBy(() -> voucherService.deleteAssignVoucher(walletRequest))
                    .isInstanceOf(CustomerIsNotExistsException.class)
                    .hasMessage("[ERROR] 해당 이메일로 저장된 고객이 없습니다.");
            }
        }

        @Nested
        @DisplayName("고객은 존재하지만 고객에게 할당된 바우처가 없다면")
        class Context_with_saved_customer_and_not_saved_voucher {
            final Customer customer = customer();
            final WalletRequest walletRequest = walletRequest();

            @BeforeEach
            void prepare() {
                given(customerRepository.findByEmail(any(String.class))).willReturn(Optional.of(customer));
                given(voucherRepository.findByCustomerId(any(UUID.class))).willReturn(Collections.emptyList());
            }

            @Test
            @DisplayName("예외를 발생한다.")
            void it_throws_exception() {
                assertThatThrownBy(() -> voucherService.deleteAssignVoucher(walletRequest))
                    .isInstanceOf(NotFoundVoucherException.class)
                    .hasMessage("[ERROR] 고객이 가진 바우처들에서 해당 아이디를 가진 바우처를 찾을 수 없습니다.");
            }
        }

    }
}
