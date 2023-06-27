package co.programmers.voucher_management.view;

import java.io.IOException;

import co.programmers.voucher_management.voucher.dto.Response;

public interface OutputView {
	void printGuideMessage() throws IOException;

	void println(Object content) throws IOException;

	void print(Object content) throws IOException;

	void print(Response contents);
}
