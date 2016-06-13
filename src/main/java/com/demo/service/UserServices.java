package com.demo.service;

import com.demo.mode.User;

public interface UserServices {

	User getUserByMobile(String mobile);

	void updateNameByMobile(String name, String mobile);

	void updateUserTransactionalSupport(String name, String mobile);
}
