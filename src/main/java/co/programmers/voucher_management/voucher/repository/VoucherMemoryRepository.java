package co.programmers.voucher_management.voucher.repository;

import static co.programmers.voucher_management.customer.entity.Status.*;

import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import co.programmers.voucher_management.voucher.entity.Status;
import co.programmers.voucher_management.customer.entity.Customer;
import co.programmers.voucher_management.exception.NoSuchDataException;
import co.programmers.voucher_management.voucher.entity.DiscountStrategy;
import co.programmers.voucher_management.voucher.entity.Voucher;
import co.programmers.voucher_management.voucher.service.DiscountTypeGenerator;

@Repository
@Profile("local")
public class VoucherMemoryRepository implements VoucherRepository {
	private static final int VOUCHER_ID_RANDOMNESS = 1000;
	private static final Random random = new Random();
	private final Map<Long, Voucher> repository = new ConcurrentHashMap<>();

	@Override
	public Voucher create(Voucher voucher) {
		long id = assignId();
		voucher.assignId(id);
		repository.put(id, voucher);
		return voucher;
	}

	public List<Voucher> findAll() {
		return repository.values()
				.stream()
				.filter(v -> v.getStatus().equals(NORMAL))
				.collect(Collectors.toList());
	}

	@Override
	public Optional<Voucher> findById(long id) {
		Voucher foundVoucher = repository.get(id);
		Status status = foundVoucher.getStatus();
		if (status.equals(DELETED)) {
			throw new NoSuchDataException(MessageFormat.format("No such voucher of id {0}", id));
		}
		return Optional.of(foundVoucher);
	}

	@Override
	public void deleteById(long id) {
		findById(id);
		repository.get(id)
				.delete();
	}

	@Override
	public Voucher update(Voucher voucher) {
		long id = voucher.getId();
		findById(id);
		String discountType = voucher.getDiscountStrategy().getType();
		Integer discountAmount = voucher.getDiscountStrategy().getAmount();
		DiscountStrategy discountStrategy = DiscountTypeGenerator.of(discountType, discountAmount);
		repository.get(id).changeDiscountType(discountStrategy);
		return findById(id).orElseThrow(
				() -> new NoSuchDataException(MessageFormat.format("No such voucher of id {0}", id))
		);
	}

	@Override
	public Voucher assignCustomer(Voucher voucher, Customer customer) {
		long id = voucher.getId();
		update(voucher);
		return findById(id).orElseThrow(
				() -> new NoSuchDataException(MessageFormat.format("No such voucher of id {0}", id)));
	}

	@Override
	public List<Voucher> findByCustomerId(long customerId) {
		return repository.values()
				.stream()
				.filter(v -> v.getCustomerId() == customerId)
				.collect(Collectors.toList());
	}

	private Long assignId() {
		random.setSeed(System.currentTimeMillis());
		return Long.parseLong(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyMMddHHmmssSS"))
				+ random.nextInt(VOUCHER_ID_RANDOMNESS));
	}

}
