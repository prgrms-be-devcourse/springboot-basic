package kr.co.programmers.springbootbasic.wallet.service;

import kr.co.programmers.springbootbasic.customer.domain.Customer;
import kr.co.programmers.springbootbasic.customer.domain.CustomerStatus;
import kr.co.programmers.springbootbasic.customer.dto.CustomerDto;
import kr.co.programmers.springbootbasic.util.ApplicationUtils;
import kr.co.programmers.springbootbasic.voucher.domain.Voucher;
import kr.co.programmers.springbootbasic.voucher.domain.VoucherType;
import kr.co.programmers.springbootbasic.voucher.dto.VoucherDto;
import kr.co.programmers.springbootbasic.wallet.domain.Wallet;
import kr.co.programmers.springbootbasic.wallet.dto.CustomerWalletDto;
import kr.co.programmers.springbootbasic.wallet.dto.WalletSaveResponseDto;
import kr.co.programmers.springbootbasic.wallet.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class WalletService {
    private final WalletRepository repository;

    public WalletService(WalletRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public WalletSaveResponseDto saveVoucherInCustomerWallet(CustomerDto customerDto, VoucherDto voucherDto) {
        String customerName = customerDto.getName();
        UUID voucherId = voucherDto.getVoucherId();
        VoucherType voucherType = voucherDto.getType();
        UUID walletId = customerDto.getWalletId();

        repository.saveVoucherInCustomerWallet(voucherId, walletId);

        // 리팩토링할 때 DTO 빌더로 바꾸기
        return new WalletSaveResponseDto(customerName, walletId, voucherId, voucherType);
    }

    public CustomerWalletDto findAllVouchersById(CustomerDto customerResponseDto) {
        String customerName = customerResponseDto.getName();
        UUID walletId = customerResponseDto.getWalletId();

        Wallet wallet = repository.findAllVouchersById(walletId);
        List<VoucherDto> voucherDtos = makeVoucherDtos(wallet);

        return new CustomerWalletDto(customerName, walletId, voucherDtos);
    }

    public Optional<CustomerDto> findCustomerByVoucherId(VoucherDto voucherDto) {
        UUID voucherId = voucherDto.getVoucherId();
        Optional<Customer> customer = repository.findCustomerByVoucherId(voucherId);

        if (customer.isPresent()) {
            Customer c = customer.get();
            UUID customerId = c.getId();
            String customerName = c.getName();
            CustomerStatus status = c.getStatus();
            UUID walletId = c.getWalletId();
            CustomerDto customerDto = new CustomerDto(customerId, customerName, status, walletId);

            return Optional.of(customerDto);
        }
        return Optional.empty();
    }

    public void deleteByVoucherId(VoucherDto voucherDto) {
        UUID voucherId = voucherDto.getVoucherId();
        repository.deleteByVoucherId(voucherId);
    }

    public CustomerWalletDto handOverVoucherToCustomer(VoucherDto voucherDto, CustomerDto customerDto) {
        UUID voucherId = voucherDto.getVoucherId();
        String customerName = customerDto.getName();
        UUID handOverWalletId = customerDto.getWalletId();
        repository.handOverVoucherToCustomer(voucherId, handOverWalletId);

        return new CustomerWalletDto(customerName, handOverWalletId, List.of(voucherDto));
    }

    private static List<VoucherDto> makeVoucherDtos(Wallet wallet) {
        List<VoucherDto> voucherDtos = new ArrayList<>();
        for (Voucher voucher : wallet.getVouchers()) {
            var dto = ApplicationUtils.convertToVoucherResponseDto(voucher);
            voucherDtos.add(dto);
        }

        return voucherDtos;
    }
}
