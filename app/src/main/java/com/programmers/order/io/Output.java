package com.programmers.order.io;

import com.programmers.order.message.ErrorMessage;
import com.programmers.order.message.Message;

public interface Output {
	// void write(BasMessage message);
	//
	void write(ErrorMessage message);

	//
	void write(String meesage);

	void write(Message message);
}


