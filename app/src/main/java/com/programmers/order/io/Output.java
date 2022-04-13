package com.programmers.order.io;

import com.programmers.order.message.BasicMessage;
import com.programmers.order.message.ErrorMessage;

public interface Output {
	void write(BasicMessage message);
	void write(ErrorMessage message);
}
