package co.programmers.voucher_management.view;

import co.programmers.voucher_management.Response;

public interface OutputView {
	void printGuideMessage();

	void println(Object content);

	void print(Object content);

	void print(Response contents);
}
