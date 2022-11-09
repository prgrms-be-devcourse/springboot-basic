package org.prgrms.springorder.io;

import java.util.List;

import org.prgrms.springorder.domain.Message;

public interface Output {

	void write(String s);
	void write(Message s);

	void writeList(List<String> list);
}
