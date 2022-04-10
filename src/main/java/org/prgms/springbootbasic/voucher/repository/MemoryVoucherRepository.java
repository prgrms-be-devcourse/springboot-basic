package org.prgms.springbootbasic.voucher.repository;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.prgms.springbootbasic.voucher.vo.Voucher;

public class MemoryVoucherRepository implements VoucherRepository{
	private final Map<UUID, Voucher> repository = new ConcurrentHashMap<>();


}
