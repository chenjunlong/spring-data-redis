package com.demo.lisenter;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

public class DemoListener implements MessageListener {

	@Override
	public void onMessage(Message message, byte[] pattern) {
		System.out.println(message.toString());
	}
}
