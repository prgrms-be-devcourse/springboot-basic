package co.programmers.voucher_management.view;

import java.io.IOException;

public interface InputView<T> {
	T input() throws IOException;
}
