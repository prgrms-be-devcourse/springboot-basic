package co.programmers.voucher.view;

import java.io.IOException;

public interface InputView<T> {
	T input() throws IOException;
}
