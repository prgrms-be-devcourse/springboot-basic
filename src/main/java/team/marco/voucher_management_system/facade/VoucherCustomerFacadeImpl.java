package team.marco.voucher_management_system.facade;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import org.springframework.stereotype.Component;
import team.marco.voucher_management_system.model.Customer;
import team.marco.voucher_management_system.model.Voucher;
import team.marco.voucher_management_system.repository.CustomerRepository;
import team.marco.voucher_management_system.repository.VoucherRepository;

@Component
public class VoucherCustomerFacadeImpl implements VoucherCustomerFacade {
    private final VoucherRepository voucherRepository;
    private final CustomerRepository customerRepository;

    public VoucherCustomerFacadeImpl(VoucherRepository voucherRepository, CustomerRepository customerRepository) {
        this.voucherRepository = voucherRepository;
        this.customerRepository = customerRepository;
    }

    @Override
    public boolean hasVoucher(String voucherId) {
        return voucherRepository.findAll()
                .stream()
                .map(Voucher::getId)
                .map(UUID::toString)
                .anyMatch(id -> Objects.equals(id, voucherId));
    }

    @Override
    public boolean hasCustomer(String customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);

        return optionalCustomer.isPresent();
    }

    @Override
    public List<Voucher> getVouchers(List<UUID> voucherIds) {
        return voucherRepository.findAll()
                .stream()
                .filter(voucher -> voucherIds.contains(voucher.getId()))
                .toList();
    }

    @Override
    public List<Customer> getCustomers(List<UUID> customerIds) {
        return customerRepository.findAll()
                .stream()
                .filter(customer -> customerIds.contains(customer.getId()))
                .toList();
    }
}
