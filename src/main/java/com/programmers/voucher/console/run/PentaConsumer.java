package com.programmers.voucher.console.run;

@FunctionalInterface
public interface PentaConsumer<T, U, V, W, X> {
	void apply(T t, U u, V v, W w, X x);
}