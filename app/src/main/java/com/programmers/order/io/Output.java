package com.programmers.order.io;

import java.io.IOException;

import com.programmers.order.message.BasicMessage;
import com.programmers.order.message.ErrorMessage;

public interface Output {
	void write(BasicMessage message) throws IOException;
	void write(ErrorMessage message) throws IOException;
}
