package co.programmers.voucher;

import java.util.Map;

public interface Launcher {

	void run(Map<String, Object> demandedData);

	Map<String, Object> getRequestBody();
}
