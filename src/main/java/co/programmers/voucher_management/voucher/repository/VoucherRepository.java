package co.programmers.voucher_management.voucher.repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import co.programmers.voucher_management.customer.entity.Customer;
import co.programmers.voucher_management.voucher.entity.Voucher;

public interface VoucherRepository {

	Voucher create(Voucher voucher);

	List<Voucher> findAll();

	Optional<Voucher> findById(long id);

	List<Voucher> findByDate(LocalDate startDate, LocalDate endDate);

	void deleteById(long id);

	Voucher update(Voucher voucher);

	Voucher assignCustomer(Voucher voucher, Customer customer);

	List<Voucher> findByCustomerId(long customerId);

	List<Voucher> findByType(String discountType);
}
