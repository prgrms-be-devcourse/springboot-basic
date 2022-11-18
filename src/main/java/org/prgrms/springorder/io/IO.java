package org.prgrms.springorder.io;

import java.util.List;

import org.prgrms.springorder.domain.Message;

public interface IO {
	String read(Message s);

	void write(String s);

	void writeList(List<String> list);
}
