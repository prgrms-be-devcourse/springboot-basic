package com.example.voucher.repository;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.example.voucher.domain.Voucher;
import com.example.voucher.utils.lineAggregator.LineAggregator;

public class FileVoucherRepository implements VoucherRepository {
	private String filePath;
	private Resource resource;
	LineAggregator lineAggregator;

	public FileVoucherRepository(LineAggregator lineAggregator) {
		this.filePath = "classpath:data/voucherList.txt";
		this.resource = new ClassPathResource(filePath);
		this.lineAggregator = lineAggregator;
	}

	@Override
	public UUID save(Voucher voucher) {
		return null;
	}

	@Override
	public List<Voucher> findAll() {
		return null;
	}
}