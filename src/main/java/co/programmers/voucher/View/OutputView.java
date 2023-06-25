package co.programmers.voucher.View;

import java.io.IOException;

public interface OutputView {
	void printGuideMessage() throws IOException;

	void println(String content) throws IOException;

	void print(String content) throws IOException;
}
