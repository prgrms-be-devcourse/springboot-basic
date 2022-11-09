package org.prgrms.springorder.domain;

@FunctionalInterface
public interface VoucherFunction<R,U,T> {
	R apply(U u,T t);
}
