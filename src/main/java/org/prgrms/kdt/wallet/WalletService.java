package org.prgrms.kdt.wallet;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.prgrms.kdt.customer.CustomerDto;
import org.prgrms.kdt.mapper.CustomerMapper;
import org.prgrms.kdt.mapper.VoucherMapper;
import org.prgrms.kdt.voucher.VoucherDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Created by yhh1056
 * Date: 2021/09/04 Time: 12:24 오후
 */
@Service
public class WalletService {

    private final WalletJdbcRepository walletJdbcRepository;

    public WalletService(WalletJdbcRepository walletJdbcRepository) {
        this.walletJdbcRepository = walletJdbcRepository;
    }

    @Transactional
    public void addWallet(WalletDto walletDto) {
        Wallet wallet = new Wallet(
                UUID.randomUUID(),
                UUID.fromString(walletDto.getCustomerId()),
                UUID.fromString(walletDto.getVoucherId()));
        walletJdbcRepository.insert(wallet);
    }

    public Optional<CustomerDto> getVouchersByCustomer(Optional<CustomerDto> customerDto) {
        return customerDto.map(this::addVoucherDtos);
    }

    private CustomerDto addVoucherDtos(CustomerDto customerDto) {
        customerDto.setVoucherDtos(
                walletJdbcRepository.findByCustomerId(UUID.fromString(customerDto.getCustomerId())).stream()
                        .map(VoucherMapper::voucherToVoucherDto)
                        .collect(Collectors.toList()));
        return customerDto;
    }

    public Optional<VoucherDto> getCustomersByVoucher(Optional<VoucherDto> voucherDto) {
        return voucherDto.map(this::addCustomerDtos);
    }

    private VoucherDto addCustomerDtos(VoucherDto voucherDto) {
        voucherDto.setCustomerDtos(
                walletJdbcRepository.findByVoucherId(UUID.fromString(voucherDto.getVoucherId())).stream()
                        .map(CustomerMapper::customerToCustomerDto)
                        .collect(Collectors.toList()));
        return voucherDto;
    }
}
