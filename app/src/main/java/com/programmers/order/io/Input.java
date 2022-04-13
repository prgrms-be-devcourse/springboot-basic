package com.programmers.order.io;

import java.io.IOException;

import com.programmers.order.message.BasicMessage;

public interface Input {
	String read(BasicMessage message) throws IOException;
}
