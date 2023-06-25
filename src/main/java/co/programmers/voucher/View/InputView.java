package co.programmers.voucher.View;

import java.io.IOException;

public interface InputView<T> {
	T input() throws IOException;
}
