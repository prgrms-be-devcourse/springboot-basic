package co.programmers.voucher.service;

import java.io.IOException;
import java.util.Map;

public interface Launcher {

	void run(Map<String, Object> demandedData) throws IOException;

	Map<String, Object> getRequestBody();
}
