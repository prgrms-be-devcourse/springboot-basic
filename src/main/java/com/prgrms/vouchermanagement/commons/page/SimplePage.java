package com.prgrms.vouchermanagement.commons.page;

public class SimplePage implements Pageable {
	private final long offset;
	private final int limit;

	private static final long DEFAULT_OFFSET = 0L;
	private static final int DEFAULT_LIMIT = 5;

	public SimplePage() {
		this(DEFAULT_OFFSET, DEFAULT_LIMIT);
	}

	public SimplePage(long offset, int limit) {
		this.offset = offset < 0 ? DEFAULT_OFFSET : offset;
		this.limit = limit < 1 ? DEFAULT_LIMIT : limit;
	}

	@Override
	public long offset() {
		return this.offset;
	}

	@Override
	public int limit() {
		return this.limit;
	}
}
