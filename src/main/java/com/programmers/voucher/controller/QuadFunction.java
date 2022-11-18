package com.programmers.voucher.controller;

@FunctionalInterface
public interface QuadFunction<T, U, V, W> {
	void apply(T t, U u, V v, W w);
}